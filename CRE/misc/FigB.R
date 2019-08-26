x <- -10:89

data1 <- read.csv("/Users/hugo/Desktop/CRE/CREV1/e2-alpha-10.0-beta-1.0.csv")

data1.stand <- scale(data1$Bias)

data2 <- read.csv("/Users/hugo/Desktop/CRE/CREV1/e2-alpha-2.0-beta-1.0.csv")

data2.stand <- scale(data2$Bias)

data3 <- read.csv("/Users/hugo/Desktop/CRE/CREV1/e2-alpha-1.0-beta-1.0.csv")

data3.stand <- scale(data3$Bias)

data4 <- read.csv("/Users/hugo/Desktop/CRE/CREV1/e2-alpha-1.0-beta-2.0.csv")

data4.stand <- scale(data4$Bias)

data5 <- read.csv("/Users/hugo/Desktop/CRE/CREV1/e2-alpha-1.0-beta-10.0.csv")

data5.stand <- scale(data5$Bias)
                     
# Create a first line
plot(x, data1.stand, type = "l", col = "red", xlab = "Bias Value (dB)", ylab = "Normalized Objective Function", 
     xlim=c(-5,80), lty = 10, lwd = 1)
# Add a second line
lines(x, data2.stand, pch = 22, col = "blue", type = "l", lty = 11, lwd = 1)

lines(x, data3.stand, pch = 22, col = "darkorange", type = "l", lty = 12, lwd = 1)

lines(x, data4.stand, pch = 22, col = "magenta", type = "l", lty = 13, lwd = 1)

lines(x, data5.stand, pch = 22, col = "brown", type = "l", lty = 14, lwd = 1)

legend("bottomright", inset=.02, 
       legend = c(expression(paste(alpha, "=10, ", beta, "=1" )), 
                  expression(paste(alpha, "=2, ", beta, "=1" )), 
                  expression(paste(alpha, "=1, ", beta, "=1" )), 
                  expression(paste(alpha, "=1, ", beta, "=2" )), 
                  expression(paste(alpha, "=1, ", beta, "=10" ))),
       col = c("red", "blue", "darkorange", "magenta", "brown"), 
       lty = c(10:14), lwd = 2, cex = 0.65)
