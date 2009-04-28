import org.apache.commons.lang.StringUtils;

@Grab(group='commons-lang', module='commons-lang', version='[2.4,2.4]')
class DtoBuilder {
	
	static {
		String.metaClass.capitalize = {
		   StringUtils.capitalize(delegate)
		}
	}

	def result = new StringWriter()
	def packageName
	def name
	def fields = [:]
	
	def build(def closure) {
		closure.delegate = this
		closure()
		
		outputPackageDefinition(0)
		
		outputBlankLine()
		
		outputClassDefinition(0)
		
		outputBlankLine()
		
		outputFieldDeclarations(1)
		
		outputBlankLine()
				
		outputBuilderClassDefinition(1)
		
		outputBlankLine()
		
		outputFieldDeclarations(2)
		
		outputBlankLine()
		
		outputBuilderConstructor(2)
		
		outputBlankLine()
		
		outputFieldBuilders(2)
		
		outputDTOFactory(2)
		
		outputBlockClose(1)
		
		outputBlankLine()
		
		outputBuilderFactory(1)
		
		outputBlankLine()

		outputDTOConstructor(1)
		
		outputBlankLine()
		
		outputFieldGetters(1)
		
		outputBlockClose(0)	
	}
	
	def outputPackageDefinition(level) {
		outputIndent(level)
		result << "package ${packageName};\n"
	}
	
	def outputBlankLine() {
		result << "\n"
	}
	
	def outputClassDefinition(level) {
		outputIndent(level)
		result << "public class ${name}DTO {\n"
	}
	
	def outputFieldDeclarations(level) {
		fields.each { name, type ->
			outputIndent(level)
			result << "private ${type} ${name};\n"			
		}
	}
	
	def outputBuilderClassDefinition(level) {
		outputIndent(level)
		result << "public static class Builder {\n"
	}
	
	def outputBuilderConstructor(level) {
		outputIndent(level)
		result << "private Builder() {}\n"
	}
	
	def outputFieldBuilders(level) {
		fields.each { name, type ->
			outputIndent(level)
			result << "public Builder ${name}(${type} ${name}) {\n"
			outputIndent(level+1)
			result << "this.${name} = ${name};\n"
			outputIndent(level+1)
			result << "return this;\n"
			outputIndent(level)
			result << "}\n"
			outputBlankLine()
		}
	}
	
	def outputDTOFactory(level) {
		outputIndent(level)
		result << "public ${name}DTO instance() {\n"
		outputIndent(level+1)
		result << "return new ${name}DTO (\n"
		fields.eachWithIndex { name, type, i ->
			outputIndent(level+2)
			result << "${name}"
			if (i < fields.size() - 1) {
				result << ","
			}
			result << "\n"
		}
		outputIndent(level+1)
		result << ");\n"
		outputIndent(level)
		result << "}\n"
	}
	
	def outputBlockClose(level) {
		outputIndent(level)
		result << "}\n"
	}
	
	def outputBuilderFactory(level) {
		outputIndent(level)
		result << "public static Builder builder() {\n"
		outputIndent(level+1)
        result << "return new Builder();\n"
		outputIndent(level)
        result << "}\n"
	}
	
	def outputDTOConstructor(level) {
		outputIndent(level)
		result << "private ${name}DTO (\n"
		
		fields.eachWithIndex { name, type, i ->
			outputIndent(level+1)
			result << "${type} ${name}"
			if (i < fields.size() - 1) {
				result << ","
			}
			result << "\n"
		}
		
		outputIndent(level)
		result << ") {\n"
		
		fields.each { name, type ->
			outputFieldInitializer(name,level+1)
		}
		
		outputIndent(level)
		result << "}\n"
	}
	
	def outputFieldGetters(level) {
		fields.each { name, type ->
			outputIndent(level)
			result << "public ${type} get${name.capitalize()}() {\n"
			outputIndent(level+1)
			result << "return ${name};\n"
			outputIndent(level)
			result << "}\n"
			outputBlankLine()
		}
	}
	
	def outputFieldInitializer(name, level) {
		outputIndent(level)
		result << "this.${name} = ${name};\n"
	}
	
	def outputIndent(level) {
		level.times {
			result << "   "
		}
	}
	
	def methodMissing(String name, args) {
		if (name == "packageName" && args[-1] instanceof String) {
			packageName = args[-1]
		}	
		else if (name == "name" && args[-1] instanceof String) {
			this.name = args[-1]
		}	
		else if (name == "field" && args[-1] instanceof Map) {
			def theMap = args[-1]
			fields["${theMap.name}"] = theMap.type
		} else {
			throw new Exception("Improper format.")
		}
	}	

}