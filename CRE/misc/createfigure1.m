function createfigure1(X1, Y1, X2)
%CREATEFIGURE1(X1, Y1, X2)
%  X1:  stairs x
%  Y1:  stairs y
%  X2:  stairs x

%  Auto-generated by MATLAB on 10-Jul-2019 16:34:41

% Create figure
figure1 = figure;

% Create axes
axes1 = axes('Parent',figure1,'FontSize',13);
box(axes1,'on');
hold(axes1,'on');

% Create ylabel
ylabel('Sine and Cosine Values');

% Create xlabel
xlabel('Sine and Cosine Values');

% Create stairs
stairs(X1,Y1,'DisplayName','\alpha = 10, \beta = 1');

% Create stairs
stairs(X2,Y1,'DisplayName','\alpha = 2, \beta = 1');

% Create legend
legend1 = legend(axes1,'show');
set(legend1,'FontSize',12);
