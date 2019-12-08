// Output created by jacc on Wed Aug 31 15:05:59 CDT 2016


import java.io.File;

class postfixCalculator implements postfixCalculatorTokens {
    private int yyss = 100;
    private int yytok;
    private int yysp = 0;
    private int[] yyst;
    protected int yyerrno = (-1);
    private int[] yysv;
    private int yyrv;

    public boolean parse() {
        int yyn = 0;
        yysp = 0;
        yyst = new int[yyss];
        yysv = new int[yyss];
        yytok = (token
                 );
    loop:
        for (;;) {
            switch (yyn) {
                case 0:
                    yyst[yysp] = 0;
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 12:
                    switch (yytok) {
                        case INTEGER:
                            yyn = 3;
                            continue;
                    }
                    yyn = 27;
                    continue;

                case 1:
                    yyst[yysp] = 1;
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 13:
                    switch (yytok) {
                        case ENDINPUT:
                            yyn = 24;
                            continue;
                        case ';':
                            yyn = 4;
                            continue;
                    }
                    yyn = 27;
                    continue;

                case 2:
                    yyst[yysp] = 2;
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 14:
                    switch (yytok) {
                        case INTEGER:
                            yyn = 3;
                            continue;
                        case 'n':
                            yyn = 6;
                            continue;
                        case ';':
                        case ENDINPUT:
                            yyn = yyr2();
                            continue;
                    }
                    yyn = 27;
                    continue;

                case 3:
                    yyst[yysp] = 3;
                    yysv[yysp] = (yylval
                                 );
                    yytok = (yylex()
                            );
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 15:
                    switch (yytok) {
                        case error:
                            yyn = 27;
                            continue;
                    }
                    yyn = yyr8();
                    continue;

                case 4:
                    yyst[yysp] = 4;
                    yysv[yysp] = (yylval
                                 );
                    yytok = (yylex()
                            );
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 16:
                    switch (yytok) {
                        case INTEGER:
                            yyn = 3;
                            continue;
                    }
                    yyn = 27;
                    continue;

                case 5:
                    yyst[yysp] = 5;
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 17:
                    yyn = yys5();
                    continue;

                case 6:
                    yyst[yysp] = 6;
                    yysv[yysp] = (yylval
                                 );
                    yytok = (yylex()
                            );
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 18:
                    switch (yytok) {
                        case error:
                            yyn = 27;
                            continue;
                    }
                    yyn = yyr7();
                    continue;

                case 7:
                    yyst[yysp] = 7;
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 19:
                    switch (yytok) {
                        case INTEGER:
                            yyn = 3;
                            continue;
                        case 'n':
                            yyn = 6;
                            continue;
                        case ';':
                        case ENDINPUT:
                            yyn = yyr1();
                            continue;
                    }
                    yyn = 27;
                    continue;

                case 8:
                    yyst[yysp] = 8;
                    yysv[yysp] = (yylval
                                 );
                    yytok = (yylex()
                            );
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 20:
                    switch (yytok) {
                        case error:
                            yyn = 27;
                            continue;
                    }
                    yyn = yyr5();
                    continue;

                case 9:
                    yyst[yysp] = 9;
                    yysv[yysp] = (yylval
                                 );
                    yytok = (yylex()
                            );
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 21:
                    switch (yytok) {
                        case error:
                            yyn = 27;
                            continue;
                    }
                    yyn = yyr3();
                    continue;

                case 10:
                    yyst[yysp] = 10;
                    yysv[yysp] = (yylval
                                 );
                    yytok = (yylex()
                            );
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 22:
                    switch (yytok) {
                        case error:
                            yyn = 27;
                            continue;
                    }
                    yyn = yyr4();
                    continue;

                case 11:
                    yyst[yysp] = 11;
                    yysv[yysp] = (yylval
                                 );
                    yytok = (yylex()
                            );
                    if (++yysp>=yyst.length) {
                        yyexpand();
                    }
                case 23:
                    switch (yytok) {
                        case error:
                            yyn = 27;
                            continue;
                    }
                    yyn = yyr6();
                    continue;

                case 24:
                    return true;
                case 25:
                    yyerror("stack overflow");
                case 26:
                    return false;
                case 27:
                    yyerror("syntax error");
                    return false;
            }
        }
    }

    protected void yyexpand() {
        int[] newyyst = new int[2*yyst.length];
        int[] newyysv = new int[2*yyst.length];
        for (int i=0; i<yyst.length; i++) {
            newyyst[i] = yyst[i];
            newyysv[i] = yysv[i];
        }
        yyst = newyyst;
        yysv = newyysv;
    }

    private int yys5() {
        switch (yytok) {
            case INTEGER:
                return 3;
            case 'n':
                return 6;
            case '*':
                return 8;
            case '+':
                return 9;
            case '-':
                return 10;
            case '/':
                return 11;
        }
        return 27;
    }

    private int yyr1() { // prog : prog ';' exp
        { System.out.println(yysv[yysp-1]); }
        yysv[yysp-=3] = yyrv;
        return 1;
    }

    private int yyr2() { // prog : exp
        { System.out.println(yysv[yysp-1]); }
        yysv[yysp-=1] = yyrv;
        return 1;
    }

    private int yyr3() { // exp : exp exp '+'
        { yyrv = yysv[yysp-3] + yysv[yysp-1];                         }
        yysv[yysp-=3] = yyrv;
        return yypexp();
    }

    private int yyr4() { // exp : exp exp '-'
        { yyrv = yysv[yysp-3] - yysv[yysp-1];                         }
        yysv[yysp-=3] = yyrv;
        return yypexp();
    }

    private int yyr5() { // exp : exp exp '*'
        { yyrv = yysv[yysp-3] * yysv[yysp-1];                         }
        yysv[yysp-=3] = yyrv;
        return yypexp();
    }

    private int yyr6() { // exp : exp exp '/'
        { yyrv = yysv[yysp-3] / yysv[yysp-1];                         }
        yysv[yysp-=3] = yyrv;
        return yypexp();
    }

    private int yyr7() { // exp : exp 'n'
        { yyrv = -yysv[yysp-2];                             }
        yysv[yysp-=2] = yyrv;
        return yypexp();
    }

    private int yyr8() { // exp : INTEGER
        { yyrv = yysv[yysp-1];                              }
        yysv[yysp-=1] = yyrv;
        return yypexp();
    }

    private int yypexp() {
        switch (yyst[yysp-1]) {
            case 4: return 7;
            case 0: return 2;
            default: return 5;
        }
    }

    protected String[] yyerrmsgs = {
    };


private void yyerror(String msg) {
    System.out.println("ERROR: " + msg);
    System.exit(1);
  }

  private int c;

  private void nextChar() {
    if (c>=0) {
      try {
        c = System.in.read();
      } catch (Exception e) {
        c = (-1);
      }
    }
  }
  
  int token;
  int yylval;
  
  int yylex() {
    for (;;) {
      // Skip whitespace
      while (c==' ' || c=='\n' || c=='\t' || c=='\r') {
        nextChar();
      }
      if (c<0) {
        return (token=ENDINPUT);
      }
      switch (c) {
        case '+' : nextChar();
                   return token='+';
        case '-' : nextChar();
                   return token='-';
        case '*' : nextChar();
                   return token='*';
        case '/' : nextChar();
                   return token='/'; 
                case ';' : nextChar();
                   return token=';';
        default  : if (Character.isDigit((char)c)) {
                     int n = 0;
                       do {
                         n = 10*n + (c - '0');
                         nextChar();
                       } while (Character.isDigit((char)c));
                       yylval = n;
                       return token=INTEGER;
                     } else {
                       yyerror("Illegal character "+c);
                       nextChar();
                     }
      }
    }
  }
  
  public static void main(String[] args) {
    postfixCalculator calc = new postfixCalculator();
    calc.nextChar(); // prime the character input stream
    calc.yylex();    // prime the token input stream
    calc.parse();    // parse the input
  }

}
