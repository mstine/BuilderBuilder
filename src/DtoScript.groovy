def bldr = new DtoBuilder()

println bldr.build {
	packageName 'edu.bogusu.registration'
	name 'Student'
	fields {
		id 'String'
		firstName 'String'
		lastName 'String'
		hoursEarned 'int'
		gpa 'float'
	}
}