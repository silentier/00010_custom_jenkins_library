def call ( Map popertyInfo ){

    node ("k8s_master") {

        stage ("custom Stages") {
            def conf = "app/conf.txt"
            props = readProperties file : conf

            checkFile();
        }
    }

}