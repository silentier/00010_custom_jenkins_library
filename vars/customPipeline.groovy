def call ( Map popertyInfo ){

    node ("santiago") {

        stage ("custom Stages") {
            def conf = "app/conf.txt"
            props = readProperties file : conf

            checkFile();
        }
    }

}