grammar ArrayInit;

init: '{' value (',' value)* '}';
value: init | INT;
INT: [0-9]+;
WS: [ \t\r\n]+ -> skip;

// sample to configure antlr
// on intelliJ we can run tests on this file