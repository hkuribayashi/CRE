# Grouped Bar Plot

data = cbind(c(0.28, 0.41, 0.62, 0.574), 
             c(0.29, 0.44, 0.623, 0.585), 
             c(0.3, 0.43, 0.637, 0.6), 
             c(0.28, 0.453, 0.651, 0.622), 
             c(0.39, 0.586, 0.747, 0.744))

colnames(data)=c("[10,1]","[2,1]","[1,1]","[1,2]","[1,10]")

rownames(data)=c("No Bias","UCB 20 dB", "UCB 40 dB", "UCB 60 dB")

dev.off()

layout(rbind(1,2), heights=c(7,1))  # put legend on bottom 1/8th of the chart

barplot(data,
        xlab="[alpha, beta]", 
        ylab="Normalized Objective Function",
        density=c(40,100,35,25) , angle=c(135,0,0,45) , col="skyblue3",
        border=c("black"),
        beside=TRUE,
        space=c(0.15,0.15,0.15,0.15,0.5,0.15,0.15,0.15,0.5,0.15,0.15,0.15,0.5,0.15,0.15,0.15,0.5,0.15,0.15,0.15))

par(mar=c(0, 0, 0, 0))

plot.new()

#legend("center", c("No Bias","UCB 20 dB", "UCB 40 dB", "UCB 60 dB"), fill=c("tomato", "skyblue3", "green", "brown"), horiz=TRUE, cex=0.8)

legend("center", 
       c("No Bias","UCB 20 dB", "UCB 40 dB", "UCB 60 dB"), 
       density=c(40,100,35,25), 
       angle=c(135,0,0,45), 
       fill="skyblue3", 
       horiz=TRUE, 
       cex=0.8)