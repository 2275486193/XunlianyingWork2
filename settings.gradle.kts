pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/google")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/central")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/androidx")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://repo.huaweicloud.com/repository/maven/google")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://repo.huaweicloud.com/repository/maven/central")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://repo.huaweicloud.com/repository/maven/gradle-plugin")
            metadataSources { mavenPom(); artifact() }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/google")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/central")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/androidx")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://repo.huaweicloud.com/repository/maven/google")
            metadataSources { mavenPom(); artifact() }
        }
        maven {
            url = uri("https://repo.huaweicloud.com/repository/maven/central")
            metadataSources { mavenPom(); artifact() }
        }
    }
}

rootProject.name = "W2"
include(":app")
