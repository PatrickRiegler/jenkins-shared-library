def call() {
    def jobContext = [:]

    node() {
        stage("Setup") {
            // generate version number
            jobContext.currentBuildVersion = sh(returnStdout: true, script: 'date +%Y%m%d%H%M%S  -u').trim()

            echo "${jobContext}"
        }
    }
    return jobContext
}
