# Library
library(dygraphs)
library(xts)          # To make the convertion data-frame / xts format

# Create data + verify it is date format + change them to xts format:
data=data.frame(time=seq(from=Sys.Date()-40, to=Sys.Date(), by=1 ), value=runif(41))
str(data$time)
data=xts(x = data$value, order.by = data$time)

# Default = line plot --> See chart #316

# Add points
dygraph(data) %>%
  dyOptions( drawPoints = TRUE, pointSize = 4 )

dy = c(1729, 1871, 1921, 1921)
dx = c(0,1,3,20)

# Create data + verify it is date format + change them to xts frmat:
data=data.frame(dx, dy)

# Default = line plot --> See chart #316

dygraph(data) %>%
  dyOptions( fillGraph=TRUE )

dygraph(data) %>%
  dyOptions( stepPlot=TRUE, fillGraph=TRUE)


