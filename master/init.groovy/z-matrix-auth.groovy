import org.jenkinsci.plugins.matrixauth.*
import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()
def env = System.getenv()
String myGroup = new File('/tmp/ldap_group').text
//String myGroup= env['LDAP_GROUP']
def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, myGroup)
strategy.add(Jenkins.ADMINISTER, "admin")
instance.setAuthorizationStrategy(strategy)

instance.save()
