def call ( Map popertyInfo ) {

    node("k8s_master") {
        stage ("Generate Docker") {
            def conf = "app/conf.txt"
            props = readProperties file: conf

            sh("ls -la")
            sh("docker build -t " + props.dockerRepository + ":" + props.deockerDefaultTag + " .")

        }
    }
}