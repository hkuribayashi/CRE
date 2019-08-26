# Grouped Bar Plot

data = cbind(c(1.0127, 1.1318), 
             c(0.8746, 1.0826), 
             c(0.8639, 0.9661), 
             c(1.0301, 1.1359), 
             c(0.7852, 0.9152))

colnames(data)=c("[10,1]","[2,1]","[1,1]","[1,2]","[1,10]")

rownames(data)=c("UCB 30 dB","PCB")

dev.off()

layout(rbind(1,2), heights=c(7,1))  

barplot(data,
        xlab="[alpha, beta]", 
        ylab="Average DL Rate per User [Mbps]",
        density=c(40,100) , angle=c(135,0) , col="skyblue3",
        border=c("black"),
        beside=TRUE)

par(mar=c(0, 0, 0, 0))

plot.new()

legend("center", 
       c("UCB 30 dB","PCB"),
       density=c(40,100), 
       angle=c(135,0), 
       fill="skyblue3", 
       horiz=TRUE, 
       cex=0.8)