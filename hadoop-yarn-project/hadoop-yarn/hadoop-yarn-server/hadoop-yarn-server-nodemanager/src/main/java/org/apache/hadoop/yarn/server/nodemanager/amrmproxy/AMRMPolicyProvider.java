package org.apache.hadoop.yarn.server.nodemanager.amrmproxy;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.ha.HAServiceProtocol;
import org.apache.hadoop.security.authorize.PolicyProvider;
import org.apache.hadoop.security.authorize.Service;
import org.apache.hadoop.yarn.api.ApplicationClientProtocolPB;
import org.apache.hadoop.yarn.api.ApplicationMasterProtocolPB;
import org.apache.hadoop.yarn.api.ContainerManagementProtocolPB;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.server.api.ResourceManagerAdministrationProtocolPB;
import org.apache.hadoop.yarn.server.api.ResourceTrackerPB;

/**
 * {@link PolicyProvider} for AMRMProxyService protocols. Copy from RMPolicyProvider
 */
public class AMRMPolicyProvider extends PolicyProvider {

    private static AMRMPolicyProvider amRmPolicyProvider = null;

    private AMRMPolicyProvider() {}

    @InterfaceAudience.Private
    @InterfaceStability.Unstable
    public static AMRMPolicyProvider getInstance() {
        if (amRmPolicyProvider == null) {
            synchronized(AMRMPolicyProvider.class) {
                if (amRmPolicyProvider == null) {
                    amRmPolicyProvider = new AMRMPolicyProvider();
                }
            }
        }
        return amRmPolicyProvider;
    }

    private static final Service[] amRmProxyServices = new Service[] {
      new Service(
              YarnConfiguration.YARN_SECURITY_SERVICE_AUTHORIZATION_RESOURCETRACKER_PROTOCOL,
              ResourceTrackerPB.class),
      new Service(
              YarnConfiguration.YARN_SECURITY_SERVICE_AUTHORIZATION_APPLICATIONCLIENT_PROTOCOL,
              ApplicationClientProtocolPB.class),
      new Service(
              YarnConfiguration.YARN_SECURITY_SERVICE_AUTHORIZATION_APPLICATIONMASTER_PROTOCOL,
               ApplicationMasterProtocolPB.class),
      new Service(
              YarnConfiguration.YARN_SECURITY_SERVICE_AUTHORIZATION_RESOURCEMANAGER_ADMINISTRATION_PROTOCOL,
              ResourceManagerAdministrationProtocolPB.class),
      new Service(
              YarnConfiguration.YARN_SECURITY_SERVICE_AUTHORIZATION_CONTAINER_MANAGEMENT_PROTOCOL,
              ContainerManagementProtocolPB.class),
      new Service(
              CommonConfigurationKeys.SECURITY_HA_SERVICE_PROTOCOL_ACL,
              HAServiceProtocol.class),
    };

    @Override
    public Service[] getServices() {
        return amRmProxyServices;
    }

}
