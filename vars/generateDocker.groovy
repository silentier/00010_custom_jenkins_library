def call ( Map popertyInfo ){
    podTemplate(yaml: '''
apiVersion: v1
kind: Pod
metadata:
  namespace: devops-tools
spec:
  containers:
  - name: maven
    image: silentier/00010_golden_image_slave_jenkins:latest
    command:
    - sleep
    args:
    - 99d
    volumeMounts:
     - mountPath: "/root/.m2/"
       name: mvn-repository
  volumes:
    - name: mvn-repository
      persistentVolumeClaim:
        claimName: mvn-repository-vol-claim
''') {
        node(POD_LABEL) {
            container('maven') {
                stage("Generate and push docker") {
                    script {
                        checkout scm

                        def conf = "app/conf.txt"
                        props = readProperties file: conf


                        sh 'docker login -u $DOCKERHUB_CREDENTIALS_USR -p $DOCKERHUB_CREDENTIALS_PSW '

                        sh("docker build -t " + props.dockerRepository + ":" + props.deockerDefaultTag + " .")
                        sh("docker push " + props.dockerRepository + ":" + props.deockerDefaultTag + " ")
                    }
                }
            }
        }
    }
}

