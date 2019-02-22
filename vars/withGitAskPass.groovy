#!/usr/bin/env groovy

void call(String credentials, Closure body) {
  String gitAskPassFilePath = pwd(tmp:true) + '/' + UUID.randomUUID().toString()
  try {
    def gitAskPassFile = libraryResource("scripts/git_askpass.sh")
    writeFile(file: gitAskPassFilePath, text: gitAskPassFile, encoding:'utf-8')
    sh "chmod 700 \"${gitAskPassFilePath}\""

    withEnv(["GIT_ASKPASS=${gitAskPassFilePath}"]) {
      withCredentials([
        usernamePassword(
          credentialsId: credentials,
          usernameVariable: 'GIT_USER',
          passwordVariable: 'GIT_PASSWORD'
        )
      ]) {
        body()
      }
    }
  } finally {
    sh "rm -f \"${gitAskPassFilePath}\""
  }
}
