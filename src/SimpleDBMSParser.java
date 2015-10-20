/* Generated By:JavaCC: Do not edit this line. SimpleDBMSParser.java */
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.ClassNotFoundException;

import schema.Table;
import schema.TableElement;
import schema.Column;
import schema.TableConstraint;
import schema.tableConstraint.*;
import schema.column.*;
import schema.exception.*;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class SimpleDBMSParser implements SimpleDBMSParserConstants {
  public enum Message
  {
    NONE,
    PRINT_SYNTAX_ERROR,
    PRINT_CREATE_TABLE,
    PRINT_DROP_TABLE,
    PRINT_DESC,
    PRINT_SHOW_TABLES,
    PRINT_SELECT,
    PRINT_INSERT,
    PRINT_DELETE,
  };

  public static Message q;

  public static Environment myDbEnvironment = null;
  public static Database myDatabase = null;

  public static HashMap<String, Table> tables = new HashMap<String, Table>();

  public static void main(String args[]) throws ParseException
  {
    SimpleDBMSParser parser = new SimpleDBMSParser(System.in);

    EnvironmentConfig envConfig = new EnvironmentConfig();
    envConfig.setAllowCreate(true);
    myDbEnvironment = new Environment(new File("db/"), envConfig);

    DatabaseConfig dbConfig = new DatabaseConfig();
    dbConfig.setAllowCreate(true);
    dbConfig.setSortedDuplicates(true);
    myDatabase = myDbEnvironment.openDatabase(null, "sampleDatabase", dbConfig);

    tables = (HashMap<String, Table>) load("tables-schema");
    if (tables == null) tables = new HashMap<String, Table>();

    while (true)
    {
      try
      {
        System.out.print("DB_2014-11663> ");
        parser.command();
      }
      catch (Exception e)
      {
        printMessage(Message.PRINT_SYNTAX_ERROR);
        SimpleDBMSParser.ReInit(System.in);
      }
    }
  }

  public static void printMessage(Message q)
  {
    switch(q)
    {
      case PRINT_SYNTAX_ERROR:
        System.out.println("Syntax error");
        break;
      case PRINT_CREATE_TABLE:
        break;
      case PRINT_DROP_TABLE:
        break;
      case PRINT_DESC:
        break;
      case PRINT_SHOW_TABLES:
        break;
      case PRINT_INSERT:
        System.out.println("\u005c'INSERT\u005c' requested");
        break;
      case PRINT_DELETE:
        System.out.println("\u005c'DELETE\u005c' requested");
        break;
      case PRINT_SELECT:
        System.out.println("\u005c'SELECT\u005c' requested");
        break;
      default:
        break;
    }
  }

  public static byte[] toBytes(Object object){
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    try {
      ObjectOutputStream out = new ObjectOutputStream(byteOut);
      out.writeObject(object);
      return byteOut.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Object fromBytes(byte[] bytes){
    ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
    try {
      ObjectInputStream in = new ObjectInputStream(byteIn);
      return in.readObject();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void save(String key, Object data){
    DatabaseEntry keyEntry;
    DatabaseEntry dataEntry;
    try {
      keyEntry = new DatabaseEntry(key.getBytes("UTF-8"));
      dataEntry = new DatabaseEntry(toBytes(data));
      myDatabase.put(null, keyEntry, dataEntry);
    } catch (DatabaseException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static Object load(String key){
    DatabaseEntry keyEntry;
    DatabaseEntry dataEntry;
    try {
      keyEntry = new DatabaseEntry(key.getBytes("UTF-8"));
      dataEntry = new DatabaseEntry();
      if (myDatabase.get(null, keyEntry, dataEntry, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
        return fromBytes(dataEntry.getData());
      } else {
        System.err.println("Can't find " + key);
        return null;
      }
    } catch (DatabaseException e) {
      e.printStackTrace();
      return null;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

 // ๋จผ์? ???? ๋ฐ์ ๊ฒ์ ?ฎ? ???ด ?? ๊ฒ๋ค?? ๋ฐ์ผ๋ก? ๋นผ๋ (? ์ข์? ?๋ฏธ๋?? ?๋ก?)
  static final public void command() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CREATE:
    case DROP:
    case DESC:
    case SHOW:
    case SELECT:
    case INSERT:
    case DELETE:
      queryList();
      break;
    case EXIT:
      jj_consume_token(EXIT);
      jj_consume_token(SEMICOLON);
      save("tables-schema", tables);
      if (myDatabase != null) myDatabase.close();
      if (myDbEnvironment != null) myDbEnvironment.close();
      System.exit(0);
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void queryList() throws ParseException {
    label_1:
    while (true) {
      q = query();
      System.out.print("DB_2014-11663> ");
      printMessage(q);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CREATE:
      case DROP:
      case DESC:
      case SHOW:
      case SELECT:
      case INSERT:
      case DELETE:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
    }
  }

  static final public Message query() throws ParseException {
  q = Message.NONE;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CREATE:
      createTableQuery();
      q = Message.PRINT_CREATE_TABLE;
      break;
    case DROP:
      dropTableQuery();
      q = Message.PRINT_DROP_TABLE;
      break;
    case SHOW:
      showQuery();
      q = Message.PRINT_SHOW_TABLES;
      break;
    case DESC:
      descQuery();
      q = Message.PRINT_DESC;
      break;
    case SELECT:
      selectQuery();
      q = Message.PRINT_SELECT;
      break;
    case INSERT:
      insertQuery();
      q = Message.PRINT_INSERT;
      break;
    case DELETE:
      deleteQuery();
      q = Message.PRINT_DELETE;
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
      {if (true) return q;}
    throw new Error("Missing return statement in function");
  }

  static final public void createTableQuery() throws ParseException {
  String tableName;
  Table table;
  ArrayList<TableElement> tableElementList;
    jj_consume_token(CREATE);
    jj_consume_token(TABLE);
    tableName = tableName();
    tableElementList = tableElementList();
    jj_consume_token(SEMICOLON);
    schema.tableConstraint.ForeignKey.tables = tables;
    try {
      if (tables.containsKey(tableName)) {if (true) throw new TableExistenceError();}
      table = Table.createTable(tableName, tableElementList);
      tables.put(tableName, table);
      System.out.println("'" + tableName + "' table is created");
    } catch (CreateTableException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.err.println(getToken(0).image);
      e.printStackTrace();
    }

  }

  static final public ArrayList<TableElement> tableElementList() throws ParseException {
  ArrayList<TableElement> tableElementList = new ArrayList<TableElement>();
    jj_consume_token(LEFT_PAREN);
    tableElementList.add(tableElement());
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      jj_consume_token(COMMA);
     tableElementList.add(tableElement());
    }
    jj_consume_token(RIGHT_PAREN);
    {if (true) return tableElementList;}
    throw new Error("Missing return statement in function");
  }

  static final public TableElement tableElement() throws ParseException {
  TableElement tableElement = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LEGAL_IDENTIFIER:
      tableElement = columnDefinition();
    {if (true) return tableElement;}
      break;
    case PRIMARY:
    case FOREIGN:
      tableElement = tableConstraintDefinition();
    {if (true) return tableElement;}
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Column columnDefinition() throws ParseException {
  String columnName;
  DataType dataType;
  Boolean nullable = true;
    columnName = columnName();
    dataType = dataType();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT:
      jj_consume_token(NOT);
      jj_consume_token(NULL);
      nullable = false;
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    {if (true) return new Column(columnName, dataType, nullable);}
    throw new Error("Missing return statement in function");
  }

  static final public DataType dataType() throws ParseException {
  Integer length;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
      jj_consume_token(INT);
    {if (true) return new Int();}
      break;
    case CHAR:
      jj_consume_token(CHAR);
      jj_consume_token(LEFT_PAREN);
      jj_consume_token(INT_VALUE);
      Token token = getToken(0);
      length = Integer.parseInt(token.image);
      jj_consume_token(RIGHT_PAREN);
    {if (true) return new Char(length);}
      break;
    case DATE:
      jj_consume_token(DATE);
    {if (true) return new Date();}
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public TableConstraint tableConstraintDefinition() throws ParseException {
  TableConstraint tableConstratint;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PRIMARY:
      tableConstratint = primaryKeyConstraint();
    {if (true) return tableConstratint;}
      break;
    case FOREIGN:
      tableConstratint = referentialConstraint();
    {if (true) return tableConstratint;}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public PrimaryKey primaryKeyConstraint() throws ParseException {
  ArrayList<String> columnNameList;
    jj_consume_token(PRIMARY);
    jj_consume_token(KEY);
    columnNameList = columnNameList();
    {if (true) return new PrimaryKey(columnNameList);}
    throw new Error("Missing return statement in function");
  }

  static final public schema.tableConstraint.ForeignKey referentialConstraint() throws ParseException {
  ArrayList<String> columnNameList;
  String tableName;
  ArrayList<String> referenceColumnNameList;
    jj_consume_token(FOREIGN);
    jj_consume_token(KEY);
    columnNameList = columnNameList();
    jj_consume_token(REFERENCES);
    tableName = tableName();
    referenceColumnNameList = columnNameList();
    {if (true) return new schema.tableConstraint.ForeignKey(columnNameList, tableName, referenceColumnNameList);}
    throw new Error("Missing return statement in function");
  }

  static final public void dropTableQuery() throws ParseException {
  ArrayList<String> tableNameList;
    jj_consume_token(DROP);
    jj_consume_token(TABLE);
    tableNameList = tableNameList();
    jj_consume_token(SEMICOLON);
    try {
      if (tableNameList == null) {
        tables = new HashMap<String, Table>();
        System.out.println("Every table is dropped");
      }
      else {
        for (String tableName : tableNameList) {
          if (!tables.containsKey(tableName)) {
            System.out.println("No such table");
          } else if (tables.get(tableName).isReferenced(new ArrayList<Table>(tables.values()))) {
            System.out.println("Drop table has failed: '" + tableName + "' is referenced by other table");
          } else {
            tables.remove(tableName);
            System.out.println("'" + tableName + "' table is dropped");
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static final public void showQuery() throws ParseException {
    jj_consume_token(SHOW);
    jj_consume_token(TABLES);
    jj_consume_token(SEMICOLON);
    for (String tableName : tables.keySet()) {
      System.out.println(tableName);
    }
  }

  static final public void descQuery() throws ParseException {
  ArrayList<String> tableNameList;
    jj_consume_token(DESC);
    tableNameList = tableNameList();
    jj_consume_token(SEMICOLON);
    try {
      if (tableNameList == null) {
        if (tables.values().size() == 0) {
          {if (true) return;}
        }
        System.out.println("-------------------------------------------------");
        for (Table table : tables.values())
        {
          table.describeTable();
          System.out.println("-------------------------------------------------");
        }
      } else {
        for (String tableName : tableNameList) {
          if (!tables.containsKey(tableName)) {
            System.out.println("No such table");
            {if (true) return;}
          }
        }
        System.out.println("-------------------------------------------------");
        for (String tableName : tableNameList) {
          tables.get(tableName).describeTable();
          System.out.println("-------------------------------------------------");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static final public ArrayList<String> tableNameList() throws ParseException {
  ArrayList<String> tableNameList = new ArrayList<String>();
  String tableName;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STAR:
      jj_consume_token(STAR);
    {if (true) return null;}
      break;
    case LEGAL_IDENTIFIER:
      tableName = tableName();
    tableNameList.add(tableName);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[8] = jj_gen;
          break label_3;
        }
        jj_consume_token(COMMA);
        tableName = tableName();
      tableNameList.add(tableName);
      }
    {if (true) return tableNameList;}
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public void selectQuery() throws ParseException {
    jj_consume_token(SELECT);
    selectList();
    tableExpression();
    jj_consume_token(SEMICOLON);
  }

  static final public void selectList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STAR:
      jj_consume_token(STAR);
      break;
    case LEGAL_IDENTIFIER:
      selectedColumn();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[10] = jj_gen;
          break label_4;
        }
        jj_consume_token(COMMA);
        selectedColumn();
      }
      break;
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void selectedColumn() throws ParseException {
    if (jj_2_1(5)) {
      tableName();
      jj_consume_token(PERIOD);
    } else {
      ;
    }
    columnName();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AS:
      jj_consume_token(AS);
      columnName();
      break;
    default:
      jj_la1[12] = jj_gen;
      ;
    }
  }

  static final public void tableExpression() throws ParseException {
    fromClause();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case WHERE:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_5;
      }
      whereClause();
    }
  }

  static final public void fromClause() throws ParseException {
    jj_consume_token(FROM);
    tableReferenceList();
  }

  static final public void tableReferenceList() throws ParseException {
    referedTable();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[14] = jj_gen;
        break label_6;
      }
      jj_consume_token(COMMA);
      referedTable();
    }
  }

  static final public void referedTable() throws ParseException {
    tableName();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AS:
      jj_consume_token(AS);
      tableName();
      break;
    default:
      jj_la1[15] = jj_gen;
      ;
    }
  }

  static final public void predicate() throws ParseException {
    if (jj_2_2(4)) {
      comparisonPredicate();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LEGAL_IDENTIFIER:
        nullPredicate();
        break;
      default:
        jj_la1[16] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  static final public void comparisonPredicate() throws ParseException {
    compOperand();
    compOp();
    compOperand();
  }

  static final public void compOperand() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT_VALUE:
    case DATE_VALUE:
    case CHAR_STRING:
      comparableValue();
      break;
    case LEGAL_IDENTIFIER:
      if (jj_2_3(2)) {
        tableName();
        jj_consume_token(PERIOD);
      } else {
        ;
      }
      columnName();
      break;
    default:
      jj_la1[17] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void compOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LESS:
      jj_consume_token(LESS);

      break;
    case GREATER:
      jj_consume_token(GREATER);

      break;
    case EQUAL:
      jj_consume_token(EQUAL);

      break;
    case GREATER_OR_EQUAL:
      jj_consume_token(GREATER_OR_EQUAL);

      break;
    case LESS_OR_EQUAL:
      jj_consume_token(LESS_OR_EQUAL);

      break;
    case NOT_EQUAL:
      jj_consume_token(NOT_EQUAL);

      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }

  }

  static final public void nullPredicate() throws ParseException {
    if (jj_2_4(2)) {
      tableName();
      jj_consume_token(PERIOD);
    } else {
      ;
    }
    columnName();
    jj_consume_token(IS);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT:
      jj_consume_token(NOT);
      break;
    default:
      jj_la1[19] = jj_gen;
      ;
    }
    jj_consume_token(NULL);
  }

  static final public void insertQuery() throws ParseException {
    jj_consume_token(INSERT);
    jj_consume_token(INTO);
    tableName();
    insertColumnsAndSource();
    jj_consume_token(SEMICOLON);
  }

  static final public void insertColumnsAndSource() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LEFT_PAREN:
      columnNameList();
      break;
    default:
      jj_la1[20] = jj_gen;
      ;
    }
    valueList();
  }

  static final public ArrayList<String> columnNameList() throws ParseException {
  ArrayList<String> columnNameList = new ArrayList<String>();
    jj_consume_token(LEFT_PAREN);
    columnNameList.add(columnName());
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[21] = jj_gen;
        break label_7;
      }
      jj_consume_token(COMMA);
      columnNameList.add(columnName());
    }
    jj_consume_token(RIGHT_PAREN);
    {if (true) return columnNameList;}
    throw new Error("Missing return statement in function");
  }

  static final public String columnName() throws ParseException {
  Token token;
  String columnName = "";
    jj_consume_token(LEGAL_IDENTIFIER);
    token = getToken(0);
    columnName = token.image;
    {if (true) return columnName;}
    throw new Error("Missing return statement in function");
  }

  static final public void valueList() throws ParseException {
    jj_consume_token(VALUES);
    jj_consume_token(LEFT_PAREN);
    value();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[22] = jj_gen;
        break label_8;
      }
      jj_consume_token(COMMA);
      value();
    }
    jj_consume_token(RIGHT_PAREN);
  }

  static final public void value() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NULL:
      jj_consume_token(NULL);
      break;
    case INT_VALUE:
    case DATE_VALUE:
    case CHAR_STRING:
      comparableValue();
      break;
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void comparableValue() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT_VALUE:
      jj_consume_token(INT_VALUE);
      break;
    case CHAR_STRING:
      jj_consume_token(CHAR_STRING);
      break;
    case DATE_VALUE:
      jj_consume_token(DATE_VALUE);
      break;
    default:
      jj_la1[24] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void deleteQuery() throws ParseException {
    jj_consume_token(DELETE);
    jj_consume_token(FROM);
    tableName();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case WHERE:
      whereClause();
      break;
    default:
      jj_la1[25] = jj_gen;
      ;
    }
    jj_consume_token(SEMICOLON);
  }

  static final public String tableName() throws ParseException {
  Token token;
  String tableName = "";
    jj_consume_token(LEGAL_IDENTIFIER);
    token = getToken(0);
    tableName = token.image;
        {if (true) return tableName;}
    throw new Error("Missing return statement in function");
  }

  static final public void whereClause() throws ParseException {
    jj_consume_token(WHERE);
    booleanValueExpression();
  }

  static final public void booleanValueExpression() throws ParseException {
    booleanTerm();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OR:
      jj_consume_token(OR);
      booleanValueExpression();
      break;
    default:
      jj_la1[26] = jj_gen;
      ;
    }
  }

  static final public void booleanTerm() throws ParseException {
    booleanFactor();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
      jj_consume_token(AND);
      booleanTerm();
      break;
    default:
      jj_la1[27] = jj_gen;
      ;
    }
  }

  static final public void booleanFactor() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT:
      jj_consume_token(NOT);
      break;
    default:
      jj_la1[28] = jj_gen;
      ;
    }
    booleanTest();
  }

  static final public void booleanTest() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LEGAL_IDENTIFIER:
    case INT_VALUE:
    case DATE_VALUE:
    case CHAR_STRING:
      predicate();
      break;
    case LEFT_PAREN:
      parenthesizedBooleanExpression();
      break;
    default:
      jj_la1[29] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void parenthesizedBooleanExpression() throws ParseException {
    jj_consume_token(LEFT_PAREN);
    booleanValueExpression();
    jj_consume_token(RIGHT_PAREN);
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  static private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  static private boolean jj_3_4() {
    if (jj_3R_9()) return true;
    if (jj_scan_token(PERIOD)) return true;
    return false;
  }

  static private boolean jj_3R_21() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(46)) {
    jj_scanpos = xsp;
    if (jj_scan_token(51)) {
    jj_scanpos = xsp;
    if (jj_scan_token(48)) return true;
    }
    }
    return false;
  }

  static private boolean jj_3_3() {
    if (jj_3R_9()) return true;
    if (jj_scan_token(PERIOD)) return true;
    return false;
  }

  static private boolean jj_3R_14() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_3()) jj_scanpos = xsp;
    if (jj_3R_22()) return true;
    return false;
  }

  static private boolean jj_3R_11() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_13()) {
    jj_scanpos = xsp;
    if (jj_3R_14()) return true;
    }
    return false;
  }

  static private boolean jj_3R_13() {
    if (jj_3R_21()) return true;
    return false;
  }

  static private boolean jj_3R_10() {
    if (jj_3R_11()) return true;
    if (jj_3R_12()) return true;
    if (jj_3R_11()) return true;
    return false;
  }

  static private boolean jj_3R_20() {
    if (jj_scan_token(NOT_EQUAL)) return true;
    return false;
  }

  static private boolean jj_3R_19() {
    if (jj_scan_token(LESS_OR_EQUAL)) return true;
    return false;
  }

  static private boolean jj_3_2() {
    if (jj_3R_10()) return true;
    return false;
  }

  static private boolean jj_3R_9() {
    if (jj_scan_token(LEGAL_IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3R_18() {
    if (jj_scan_token(GREATER_OR_EQUAL)) return true;
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_3R_9()) return true;
    if (jj_scan_token(PERIOD)) return true;
    return false;
  }

  static private boolean jj_3R_17() {
    if (jj_scan_token(EQUAL)) return true;
    return false;
  }

  static private boolean jj_3R_16() {
    if (jj_scan_token(GREATER)) return true;
    return false;
  }

  static private boolean jj_3R_22() {
    if (jj_scan_token(LEGAL_IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3R_15() {
    if (jj_scan_token(LESS)) return true;
    return false;
  }

  static private boolean jj_3R_12() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_15()) {
    jj_scanpos = xsp;
    if (jj_3R_16()) {
    jj_scanpos = xsp;
    if (jj_3R_17()) {
    jj_scanpos = xsp;
    if (jj_3R_18()) {
    jj_scanpos = xsp;
    if (jj_3R_19()) {
    jj_scanpos = xsp;
    if (jj_3R_20()) return true;
    }
    }
    }
    }
    }
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public SimpleDBMSParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[30];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x48b4060,0x48b4040,0x48b4040,0x0,0xc00,0x80000000,0x380,0xc00,0x0,0x100000,0x0,0x100000,0x200000,0x8000000,0x0,0x200000,0x0,0x0,0x0,0x80000000,0x0,0x0,0x0,0x0,0x0,0x8000000,0x10000000,0x20000000,0x80000000,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x10,0x2000,0x0,0x0,0x0,0x10,0x2000,0x10,0x2000,0x0,0x0,0x10,0x0,0x2000,0x96000,0x1f80,0x0,0x4,0x10,0x10,0x94001,0x94000,0x0,0x0,0x0,0x0,0x96004,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[4];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public SimpleDBMSParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public SimpleDBMSParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new SimpleDBMSParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public SimpleDBMSParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new SimpleDBMSParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public SimpleDBMSParser(SimpleDBMSParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(SimpleDBMSParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[57];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 30; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 57; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 4; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
