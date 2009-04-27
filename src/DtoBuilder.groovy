import org.apache.commons.lang.StringUtils;

class DtoBuilder {
	
	static {
		groovy.grape.Grape.grab(group:'commons-lang', module:'commons-lang', version:'[2.4,2.4]')

		String.metaClass.capitalize = {
		   StringUtils.capitalize(delegate)
		}
	}

	def result = new StringWriter()
	def packageName
	def name
	def fields = [:]
	
	def build(closure) {
		closure.delegate = this
		closure()
		
		result << "package ${packageName};\n\n"
		result << "public class ${name}DTO {\n\n"
		
		outputFields(result, 1)
		
		result << "\n"
		
		result << "   public static class Builder {\n\n"
		
		outputFields(result, 2)
		
		result << "\n"
		
		result << "      private Builder() {}\n\n"
		
		fields.each { name, type ->
			result << "      public Builder ${name}(${type} ${name}) {\n"
			result << "         this.${name} = ${name};\n"
			result << "         return this;\n"
			result << "      }\n\n"
		}
		
		result << "      public ${name}DTO instance() {\n"
		result << "         return new ${name}DTO (\n"
		fields.eachWithIndex { name, type, i ->
			result << "            ${name}"
			if (i < fields.size() - 1) {
				result << ","
			}
			result << "\n"
		}
		result << "         );\n"
		result << "      }\n"
		
		result << "   }\n"
		
		result << """
   public static Builder builder() {
      return new Builder();
   }
"""
		result << "\n   private ${name}DTO (\n"
		
		fields.eachWithIndex { name, type, i ->
			result << "      ${type} ${name}"
			if (i < fields.size() - 1) {
				result << ","
			}
			result << "\n"
		}
		
		result << "   ) {\n"
		
		fields.each { name, type ->
			result << "      this.${name} = ${name};\n"
		}
		
		result << "   }\n\n"
		
		fields.each { name, type ->
			result << "   public ${type} get${name.capitalize()}() {\n"
			result << "      return ${name};\n"
			result << "   }\n\n"
		}
		
		result << "}"		
	}
	
	def outputFields(result, indent) {
		fields.each { name, type ->
			indent.times() {
				result << "   "
			}
			result << "private ${type} ${name};\n"			
		}
	}

	def methodMissing(String name, args) {
		if (name == "packageName") {
			packageName = args[0]
		}	
		else if (name == "name") {
			this.name = args[0]
		}	
		else if (name == "fields" && args[-1] instanceof Closure) {
			def theClosure = args[-1]
			theClosure.delegate = this
			theClosure()
		} else {
			fields["${name}"] = args[0]
		}
	}	

}