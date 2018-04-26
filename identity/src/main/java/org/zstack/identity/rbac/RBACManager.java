package org.zstack.identity.rbac;

import org.zstack.core.db.SQLBatchWithReturn;
import org.zstack.header.identity.*;
import org.zstack.header.identity.role.RoleState;
import org.zstack.header.message.APIMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface RBACManager {
    String SERVICE_ID = "rbac";

    List<PolicyInventory> internalPolices = new ArrayList<>();
    Map<PolicyInventory, List<PolicyStatement>> internalDenyStatements = new HashMap<>();
    Map<PolicyInventory, List<PolicyStatement>> internalAllowStatements = new HashMap<>();

    static List<PolicyInventory> getPoliciesByAPI(APIMessage message) {
        return new SQLBatchWithReturn<List<PolicyInventory>>() {
            @Override
            protected List<PolicyInventory> scripts() {
                SessionInventory session = message.getSession();
                List<PolicyInventory> ret = new ArrayList<>(internalPolices);
                if (session.isAccountSession()) {
                    ret.addAll(getPoliciesForAccount(session));
                } else {
                    ret.addAll(getPoliciesForUser(session));
                }

                return ret;
            }

            private List<PolicyInventory> getPoliciesForUser(SessionInventory session) {
                // polices attached to the user
                List<PolicyVO> vos = sql("select p from PolicyVO p, UserPolicyRefVO r where r.policyUuid = p.uuid" +
                        " and r.userUuid = :uuid", PolicyVO.class).param("uuid", session.getUserUuid()).list();

                // polices attached to user groups the user is in
                vos.addAll(sql("select p from PolicyVO p, UserGroupVO g, UserGroupPolicyRefVO up, UserGroupUserRefVO ugu where p.uuid = up.policyUuid" +
                        " and g.uuid = up.groupUuid and g.uuid = ugu.groupUuid and ugu.userUuid = :uuid", PolicyVO.class).param("uuid", session.getUserUuid()).list());

                // polices attached to roles the user has
                vos.addAll(sql("select p from PolicyVO p, RolePolicyRefVO rp, RoleUserRefVO ru, RoleVO role where p.uuid = rp.policyUuid and" +
                        " rp.roleUuid = ru.roleUuid and ru.userUuid = :uuid and role.uuid = rp.roleUuid and role.state = :state", PolicyVO.class)
                        .param("uuid", session.getUserUuid())
                        .param("state", RoleState.Enabled)
                        .list());

                // polices attached to roles of user groups the user is in
                vos.addAll(sql("select p from PolicyVO, RolePolicyRefVO rp, RoleUserGroupRefVO rug, UserGroupUserRefVO ugu, RoleVO role where" +
                        " p.uuid = rp.policyUuid and rp.roleUuid = rug.roleUuid and rug.groupUuid = ugu.groupUuid and ugu.userUuid = :uuid" +
                        " and rp.roleUuid = role.uuid and role.state = :state", PolicyVO.class)
                        .param("state", RoleState.Enabled)
                        .param("uuid", session.getUserUuid()).list());

                return PolicyInventory.valueOf(vos);
            }

            private List<PolicyInventory> getPoliciesForAccount(SessionInventory session) {
                List<PolicyVO> vos = sql("select p from PolicyVO p, RoleAccountRefVO ra, RoleVO role, RolePolicyRefVO rp" +
                        " where p.uuid = rp.policyUuid and rp.roleUuid = role.uuid and role.uuid = ra.roleUuid and" +
                        " ra.accountUuid = :uuid and role.state = :state", PolicyVO.class)
                        .param("state", RoleState.Enabled)
                        .param("uuid", session.getAccountUuid()).list();
                return PolicyInventory.valueOf(vos);
            }
        }.execute();
    }

    static Map<PolicyInventory, List<PolicyStatement>> collectDenyStatements(List<PolicyInventory> polices) {
        Map<PolicyInventory, List<PolicyStatement>> ret = new HashMap<>();
        polices.forEach(p -> {
            List<PolicyStatement> ss = p.getStatements().stream().filter(s->s.getEffect() == StatementEffect.Deny).collect(Collectors.toList());
            if (!ss.isEmpty()) {
                ret.put(p, ss);
            }
        });

        return ret;
    }

    static Map<PolicyInventory, List<PolicyStatement>> collectAllowedStatements(List<PolicyInventory> polices) {
        Map<PolicyInventory, List<PolicyStatement>> ret = new HashMap<>();
        polices.forEach(p -> {
            List<PolicyStatement> ss = p.getStatements().stream().filter(s->s.getEffect() == StatementEffect.Allow).collect(Collectors.toList());
            if (!ss.isEmpty()) {
                ret.put(p, ss);
            }
        });

        return ret;
    }
}