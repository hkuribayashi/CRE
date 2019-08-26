# Grouped Bar Plot

data = cbind(c(17.64, 17.64, 19.58), 
             c(14.28, 14.28, 18.84), 
             c(15.78, 15.78, 18.98), 
             c(15.78, 15.78, 17.89), 
             c(15.0, 15.0, 15.56))

colnames(data)=c("[10,1]","[2,1]","[1,1]","[1,2]","[1,10]")

rownames(data)=c("UCB 30 dB","UCB 30 dB 2", "PCB")

dev.off()

layout(rbind(1,2), heights=c(7,1))  

barplot(data,
        xlab="[alpha, beta]", 
        ylab="Ratio UEs/Serving BSs",
        density=c(40,30,100) , angle=c(135,90,0) , col="skyblue3",
        border=c("black"),
        beside=TRUE)

par(mar=c(0, 0, 0, 0))

plot.new()

legend("center", 
       c("UCB 30 dB","UCB 30 db 2","PCB"),
       density=c(40,30,100), 
       angle=c(135,90,0), 
       fill="skyblue3", 
       horiz=TRUE, 
       cex=0.8)