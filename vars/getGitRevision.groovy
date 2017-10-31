// vars/getGitRevision.groovy
def call(boolean shortRevision = true) {
    def shortOption = shortRevision ? '--short' : ''
    def gitRevision = sh(returnStdout: true, script: "git rev-parse ${shortOption} HEAD").trim()
    return gitRevision
}
