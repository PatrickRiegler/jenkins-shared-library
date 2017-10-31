// vars/defaultProperties.groovy
def call() {
    properties([
        buildDiscarder(logRotator(numToKeepStr: '5')),
        pipelineTriggers([pollSCM('H/15 * * * *')])
    ])
}
