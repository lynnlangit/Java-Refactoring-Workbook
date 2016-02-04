import java.io.PrintStream;

public class Template {

	public void applyTemplate(String sourceTemplate, String reqId, PrintStream out) {
		String code;
		String altcode;
		
		try {
			String template = new String(sourceTemplate);

			// Substitute for %CODE%
			int templateSplitBegin = template.indexOf("%CODE%");
			int templateSplitEnd = templateSplitBegin + 6;
			String templatePartOne = new String(template.substring(0, templateSplitBegin));
			String templatePartTwo = new String(template.substring(templateSplitEnd,
					template.length()));
			code = new String(reqId);
			template = new String(templatePartOne + code + templatePartTwo);

			// Substitute for %ALTCODE%
			templateSplitBegin = template.indexOf("%ALTCODE%");
			templateSplitEnd = templateSplitBegin + 9;
			templatePartOne = new String(template.substring(0, templateSplitBegin));
			templatePartTwo = new String(template.substring(templateSplitEnd, template.length()));
			altcode = code.substring(0, 5) + "-" + code.substring(5, 8);
			out.print(templatePartOne + altcode + templatePartTwo);
		} catch (Exception e) {
			System.out.println("Error in substitute()");
		}
	}
}
