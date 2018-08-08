import org.yaml.snakeyaml.Yaml

Map secret = new Yaml().load(new FileReader('/dev/shm/test.yaml'))

secret.each{ k, v -> println "${k}:${v}" }

def x = secret.find{ it.key == 'MYSECRET2'}?.value
if (x) 
println "${x}"