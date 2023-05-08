def call ( Map popertyInfo ){

    node ("k8s_master") {

        stage ("Stages") {
            checkout scm

            compile();
        }
    }

}