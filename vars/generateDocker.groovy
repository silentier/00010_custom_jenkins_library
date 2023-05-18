def call ( Map popertyInfo ) {

    node("k8s_master") {
        stage ("Generate Docker and Push") {
            def conf = "app/conf.txt"
            props = readProperties file: conf

            sh("ls -la")

            withCredentials([[$class: 'UsernamePasswordMultiBinding',
                              credentialsId: 'dockerhub',
                              usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                sh "docker login -u $USERNAME -p $PASSWORD "
            }


            sh("docker build -t " + props.dockerRepository + ":" + props.deockerDefaultTag + " .")
            sh ("docker push " + props.dockerRepository + ":" + props.deockerDefaultTag + " ")
        }
    }
}
