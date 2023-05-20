def call ( Map popertyInfo ){
    podTemplate(yaml: '''
kind: Pod
metadata:
    namespace: devops-tools
spec:
  containers:
  - name: executor
    image: gcr.io/kaniko-project/executor:latest
    command:
    - sleep
    args:
    - 99d
''')
            {
        node(POD_LABEL) {
            container('kaniko') {
                stage("Generate and push docker") {
                    script {
                        checkout scm

                        sh("cat /etc/os-release")


                        def conf = "app/conf.txt"
                        props = readProperties file: conf

                        withCredentials([[$class: 'UsernamePasswordMultiBinding',
                                          credentialsId: 'dockerhub',
                                          usernameVariable: 'USERNAME',
                                          passwordVariable: 'PASSWORD']]) {
                            sh """
                                docker login -u $USERNAME -p $PASSWORD
                            """
                        }



                    //sh("docker build -t " + props.dockerRepository + ":" + props.deockerDefaultTag + " .")
                    // sh("docker push " + props.dockerRepository + ":" + props.deockerDefaultTag + " ")

                    }
                }
            }
        }
    }
}

