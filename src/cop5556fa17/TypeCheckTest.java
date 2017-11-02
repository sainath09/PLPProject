package cop5556fa17;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

import cop5556fa17.AST.ASTNode;
import cop5556fa17.AST.ASTVisitor;
import cop5556fa17.AST.Declaration_Image;
import cop5556fa17.AST.Declaration_SourceSink;
import cop5556fa17.AST.Declaration_Variable;
import cop5556fa17.AST.Expression;
import cop5556fa17.AST.Expression_FunctionAppWithExprArg;
import cop5556fa17.AST.Expression_IntLit;
import cop5556fa17.AST.Expression_PixelSelector;
import cop5556fa17.AST.Expression_PredefinedName;
import cop5556fa17.AST.Expression_Unary;
import cop5556fa17.AST.Index;
import cop5556fa17.AST.LHS;
import cop5556fa17.AST.Program;
import cop5556fa17.AST.Source_CommandLineParam;
import cop5556fa17.AST.Source_StringLiteral;
import cop5556fa17.AST.Statement_Out;
import cop5556fa17.AST.Statement_Assign;
import cop5556fa17.Parser.SyntaxException;
import cop5556fa17.Scanner.Kind;
import cop5556fa17.Scanner.LexicalException;
import cop5556fa17.Scanner.Token;
import cop5556fa17.TypeCheckVisitor.SemanticException;

import static cop5556fa17.Scanner.Kind.*;

public class TypeCheckTest {

	// set Junit to be able to catch exceptions
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// To make it easy to print objects and turn this output on and off
	static final boolean doPrint = true;
	private void show(Object input) {
		if (doPrint) {
			System.out.println(input.toString());
		}
	}
	
	
	/**
	 * Scans, parses, and type checks given input String.
	 * 
	 * Catches, prints, and then rethrows any exceptions that occur.
	 * 
	 * @param input
	 * @throws Exception
	 */
	void typeCheck(String input) throws Exception {
		show(input);
		try {
			Scanner scanner = new Scanner(input).scan();
			ASTNode ast = new Parser(scanner).parse();
			show(ast);
			ASTVisitor v = new TypeCheckVisitor();
			ast.visit(v, null);
		} catch (Exception e) {
			show(e);
			throw e;
		}
	}

	/**
	 * Simple test case with an almost empty program.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSmallest() throws Exception {
		String input = "n"; //Smallest legal program, only has a name
		show(input); // Display the input
		Scanner scanner = new Scanner(input).scan(); // Create a Scanner and
														// initialize it
		show(scanner); // Display the Scanner
		Parser parser = new Parser(scanner); // Create a parser
		ASTNode ast = parser.parse(); // Parse the program
		TypeCheckVisitor v = new TypeCheckVisitor();
		String name = (String) ast.visit(v, null);
		show("AST for program " + name);
		show(ast);
	}



	
	/**
	 * This test should pass with a fully implemented assignment
	 * @throws Exception
	 */
	 @Test
	 public void testDec11() throws Exception {
	 String input = "prog boolean k = true | false;";
	 typeCheck(input);
	 }
	 
	 @Test
     public void test111() throws Exception {
     String input = "prog int i =  8 ;"
             + "int k;"
             + "boolean m = true | false ;"
             + "boolean n = true ;"
             + "image[x,y] im <- \"http://www.google.com/s.jpg\" ; \n\n"
             + "url sou = \"http://vsfv\";"
             + "file fie = \"filename.txt\" ;\n"
             + "image img_im;"
             + "k = 45;"
             + "k = i + 25;";
     typeCheck(input);
     }
	 
	 @Test
     public void test211() throws Exception {
	     //thrown.expect(SemanticException.class);
	     String input = "prog image [10,11] abcd <- @1234+234;";
             
	     typeCheck(input);
     }
	 @Test
     public void test31() throws Exception {
         //thrown.expect(SemanticException.class);
	     thrown.expect(SemanticException.class);
	     String input = "prog boolean i =  8 ;";
	     typeCheck(input);
     }
	 
	 @Test
     public void test41() throws Exception {
         //thrown.expect(SemanticException.class);
         thrown.expect(SemanticException.class);
         String input = "prog boolean i =  true ;\n "
                 + "image img ; "
                 + "image img;";
         typeCheck(input);
     }
	 
	 @Test
     public void test51() throws Exception {
         String input = "prog boolean i =  false ; ";
         typeCheck(input);
     }
	 
	 
     @Test
     public void testDec21() throws Exception {
     String input = "prog int k = 2;";
     typeCheck(input);
     }
     @Test
     public void testUndec23() throws Exception {
     String input = "prog int k=k+1;";
     thrown.expect(SemanticException.class);
     typeCheck(input);
     }
     @Test
     public void testUndec() throws Exception {
     String input = "prog k = 42;";
     thrown.expect(SemanticException.class);
     typeCheck(input);
     }
     
    

    
     /**
      * This program does not declare k. The TypeCheckVisitor should
      * throw a SemanticException in a fully implemented assignment.
      * @throws Exception
      */
     
     

}
