X = [0 1 2 3 15]
Y = [1690 1760 1760 1892 1892]

X2 = [0 1 2 3 15]
Y2 = [1647 1745 1886 1946 1946]

X3 = [0 1 2 3 15]
Y3 = [1562 1864 1976 2005 2005]

figure
stairs(X,Y,'-')

hold on

stairs(X2,Y2,'--')
stairs(X3,Y3,'-.m')


xlabel('PCB Steps') 
ylabel('Objective Function') 

legend('Min. PCB', 'Median PCB', 'Max. PCB')

lgd = legend;
lgd.FontSize = 11;

ax = gca;
ax.FontSize = 12;
