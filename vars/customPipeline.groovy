def call ( Map popertyInfo ){

    node {
        stage ("custom Stages") {
            def conf = "app/conf.txt"
            props = readProperties file : conf

            println props
        }
    }

}