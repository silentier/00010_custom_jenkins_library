def call ( Map popertyInfo ){
    podTemplate(yaml: '''
apiVersion: v1 
kind: Pod 
metadata: 
    name: dood 
    namespace: devops-tools 
spec: 
    containers: 
      - name: docker-cmds 
        image: docker:1.12.6 
        command: ['docker', 'run', '-p', '80:80', 'httpd:latest'] 
        resources: 
            requests: 
                cpu: 10m 
                memory: 256Mi 
        volumeMounts: 
          - mountPath: /var/run 
            name: docker-sock 
    volumes: 
      - name: docker-sock 
        hostPath: 
            path: /var/run 
''') {
        node(POD_LABEL) {
            container('docker-cmds') {
                stage("Generate docker") {

                    def conf = "app/conf.txt"
                    props = readProperties file: conf

                    println "docker build -t "+props.dockerRepository+":"+props.deockerDefaultTag+" ."
                    sh("docker build -t "+props.dockerRepository+":"+props.deockerDefaultTag+" .")
                }
            }
        }
    }
}
