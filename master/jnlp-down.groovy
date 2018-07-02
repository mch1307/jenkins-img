/*
   Disable all JNLP protocols except for JNLP4.  JNLP4 is the most secure agent
   protocol because it is using standard TLS.
   
   source: https://github.com/samrocketman/jenkins-bootstrap-shared/blob/master/scripts/configure-jnlp-agent-protocols.groovy
 */
import jenkins.model.Jenkins

Thread.start {
    sleep 3000

    println "--> Configuring JNLP Agent Protocols"
    
    Jenkins jenkins = Jenkins.instance

    if(!jenkins.isQuietingDown()) {
        Set<String> agentProtocolsList = ['JNLP4-connect', 'Ping']
        if(!jenkins.getAgentProtocols().equals(agentProtocolsList)) {
            jenkins.setAgentProtocols(agentProtocolsList)
            jenkins.save()
        }
    }
    else {
        println 'Shutdown mode enabled.  Configure Agent Protocols SKIPPED.'
    }
}