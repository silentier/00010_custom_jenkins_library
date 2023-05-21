def call ( Map popertyInfo ){
        node("k8s_master") {
            stage("Generate docker") {

                def conf = "app/conf.txt"
                props = readProperties file: conf



                step(
                        $class: "CopyArtifact",
                        fingerprintArtifacts: true,
                        target: 'myArtifact.jar',
                        flatten: true,
                )

                sh(" ls -la")
                sh("docker build -t "+props.dockerRepository+":"+props.deockerDefaultTag+" .")
            }
        }
}
