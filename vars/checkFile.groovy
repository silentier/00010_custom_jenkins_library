def call ( Map popertyInfo ){

    node ("k8s_master") {

        stage ("Check File") {
            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
        }
    }
}