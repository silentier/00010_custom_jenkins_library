def call ( Map popertyInfo ){
    podTemplate(yaml: '''
apiVersion: "v1"
kind: "Pod"
metadata:
  annotations:
    buildUrl: "http://jenkins-service.jenkins.svc.cluster.local:8080/job/testdockerbuildchatgpt/4/"
    runUrl: "job/testdockerbuildchatgpt/4/"
  labels:
    app: "my-docker-build"
    jenkins: "agent"
    jenkins/label-digest: "b5593a0e01711fc2ff02061aa718a7559c6e2f44"
    jenkins/label: "my-docker-build"
  name: "my-docker-build-md4d4-jfzc3"
  namespace: "jenkins"
spec:
  containers:
  - command:
    - "cat"
    image: "docker:latest"
    name: "docker"
    tty: true
''') {
        node(POD_LABEL) {
            container('command') {
                stage("Generate and push docker") {
                    script {
                        checkout scm

                        sh("cat /etc/os-release")

                        /*
                        def conf = "app/conf.txt"
                        props = readProperties file: conf


                        sh 'docker login -u $DOCKERHUB_CREDENTIALS_USR -p $DOCKERHUB_CREDENTIALS_PSW '

                        sh("docker build -t " + props.dockerRepository + ":" + props.deockerDefaultTag + " .")
                        sh("docker push " + props.dockerRepository + ":" + props.deockerDefaultTag + " ")
                        */
                    }
                }
            }
        }
    }
}

