def call ( Map popertyInfo ) {
    node("k8s_master") {
        stage("restart service") {
            script {

                def conf = "app/conf.txt"
                props = readProperties file: conf
                DEPLOYMENT_NAME = readProperties.deploymentName


                sh(" kubectl rollout restart deployment ${DEPLOYMENT_NAME} -n silentier ")
            }
        }
    }
}