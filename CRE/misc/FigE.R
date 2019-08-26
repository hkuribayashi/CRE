solution <- 1:5

solution[1] <- read.csv(paste("/Users/hugo/Desktop/CRE/CREV1-copy2/solution-alpha-10.0-beta-1.0.csv", sep = ""))
solution[2] <- read.csv(paste("/Users/hugo/Desktop/CRE/CREV1-copy2/solution-alpha-2.0-beta-1.0.csv", sep = ""))
solution[3] <- read.csv(paste("/Users/hugo/Desktop/CRE/CREV1-copy2/solution-alpha-1.0-beta-1.0.csv", sep = ""))
solution[4] <- read.csv(paste("/Users/hugo/Desktop/CRE/CREV1-copy2/solution-alpha-1.0-beta-2.0.csv", sep = ""))
solution[5] <- read.csv(paste("/Users/hugo/Desktop/CRE/CREV1-copy2/solution-alpha-1.0-beta-10.0.csv", sep = ""))

result <- data.frame(solution)

names(result) <- c("10-1", "2-1", "1-1", "1-2", "1-10")

boxplot(result$`10-1`, result$`2-1`, result$`1-1`, result$`1-2`, result$`1-10`,
        ylab = "CRE Bias Value (dB)", xlab = "[alpha, beta]", 
        names = c("[10,1]", "[2,1]", "[1,1]", "[1,2]", "[1,10]"))


