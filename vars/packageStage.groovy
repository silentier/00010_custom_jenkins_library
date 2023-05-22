def call ( Map popertyInfo ){
    podTemplate(yaml: '''
apiVersion: v1
kind: Pod
metadata:
  name: maven-docker
  namespace: devops-tools
spec:
  containers:
    - name: maven
      image: silentier/00010_golden_image_slave_jenkins:2023_05_20_17_39_40
      command:
        - sleep
      args:
        - 99d
      volumeMounts:
        - mountPath: "/root/.m2/"
          name: mvn-repository
    - name: docker-cmds
      image: docker:1.12.6
      command: [ 'docker', 'run', '-p', '80:80', 'httpd:latest' ]
      resources:
        requests:
          cpu: 10m
          memory: 256Mi
      volumeMounts:
        - mountPath: /var/run
          name: docker-sock
  volumes:
    - name: mvn-repository
      persistentVolumeClaim:
        claimName: mvn-repository-vol-claim
    - name: docker-sock
      hostPath:
        path: /var/run
''') {
        node(POD_LABEL) {
            container('maven') {
                stage("Package") {
                    checkout scm

                    sh("cat /etc/os-release")

                    def conf = "app/conf.txt"
                    props = readProperties file: conf

                    println props
                    println "packageMethod:" + props.packageMethod

                    switch (props.testMethod) {
                        case "mvn":
                            configFileProvider([configFile(fileId: 'd9f13ed0-a67a-4c59-81d9-f6034324ed8b', variable: 'config')]) {
                                sh("mvn -version")
                                sh("mvn " + props.packageCommand + " -s ${config} ")

                            }

                            break
                        default:
                            println "default"
                            break
                    }
                }
            }
        }
    }
}
