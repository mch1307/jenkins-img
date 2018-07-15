import com.cloudbees.plugins.credentials.impl.*;
import com.cloudbees.plugins.credentials.*;
import com.cloudbees.plugins.credentials.domains.*
import org.jenkinsci.plugins.docker.commons.credentials.DockerServerCredentials


String ucpCA = new File('/tmp/ca.pem').text
String ucpCert = new File('/tmp/cert.pem').text
String ucpKey = new File('/tmp/key.pem').text

Credentials d = (Credentials) new DockerServerCredentials(CredentialsScope.GLOBAL, "ucp-certs", "UCP Certs", ucpKey, ucpCert, ucpCA)

SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), d)
