def call(boolean withVersion = true) {
    def jobContext = [:]

    node() {
        stage("Setup") {
            if (withVersion) {
                // generate version number
                jobContext.currentBuildVersion = sh(returnStdout: true, script: 'date +%Y%m%d%H%M%S  -u').trim()
            }
            echo "${jobContext}"
        }
    }
    return jobContext
}
