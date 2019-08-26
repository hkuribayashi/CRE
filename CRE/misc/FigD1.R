# Grouped Bar Plot

data = cbind(c(0.5880, 0.9586, 0.9973), 
             c(0.7282, 0.9501, 0.9878), 
             c(0.6366, 0.9503, 0.9790), 
             c(0.6744, 0.9331, 0.9655), 
             c(0.7615, 0.9307, 0.9308))

colnames(data)=c("[10,1]","[2,1]","[1,1]","[1,2]","[1,10]")

rownames(data)=c("UCB 30 dB","UCB 30 dB 2", "PCB")

dev.off()

layout(rbind(1,2), heights=c(7,1))  # put legend on bottom 1/8th of the chart

barplot(data,
        xlab="[alpha, beta]", 
        ylab="Normalized Objective Function",
        density=c(40,30,100) , angle=c(135,90,0) , col="skyblue3",
        border=c("black"),
        beside=TRUE)

par(mar=c(0, 0, 0, 0))

plot.new()

legend("center", 
       c("UCB 30 dB","UCB 30 dB 2", "PCB"),
       density=c(40,30,100),
       angle=c(135,90,0), 
       fill="skyblue3", 
       horiz=TRUE, 
       cex=0.8)