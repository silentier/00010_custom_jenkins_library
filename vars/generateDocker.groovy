def call ( Map popertyInfo ){
        node("k8s_master") {
            stage("Generate docker") {

                def conf = "app/conf.txt"
                props = readProperties file: conf



                copyArtifacts(
                        filter: 'proyecto_00010_basic1-0.0.1-SNAPSHOT.jar',
                        projectName: env.JOB_NAME,
                        fingerprintArtifacts: true,
                        selector: specific(env.BUILD_NUMBER)
                )

                sh(" ls -la")
                sh("docker build -t "+props.dockerRepository+":"+props.deockerDefaultTag+" .")
            }
        }
}
