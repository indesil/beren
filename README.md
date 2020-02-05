Beren
========

`Beren` is a Java static bean validation generator able to generate validators classes at compilation time!
Beren is still under development phase!      

## How does it works?
You need to add two modules into your project. First `beren-compiler` 
which is responsible for annotation processing and code generation.
Second `beren-core` which contains annotations used by compiler.
During compilation time for every class annotated with `@Validator` 
corresponding java class will be generated according to given definitions.

## Configuration
*Beren is still under **implementation phase and was not uploaded into maven repository.**
During this phase you need to clone repository to have it on your local maven repository.*

Add maven dependencies to your project
```xml
<dependencies>
    <dependency>
        <groupId>io.github.indesil</groupId>
        <artifactId>beren-compiler</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>io.github.indesil</groupId>
        <artifactId>beren-core</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Beren and Bean Validation 2.0 list of features
As Beren uses code generation to create proper validators it is not possible to fully cover Bean Validation 2.0 specification.
Please look at the following list of supported and unsupported features to make sure if you can and want to use Beren in your project.

### List of Bean Validation :heavy_check_mark: supported features (or to be :heavy_plus_sign: supported in the future)
- :heavy_plus_sign: Parsing javax constraints annotations
- :heavy_plus_sign: Messages internationalization
- :heavy_plus_sign: Annotation definitions target
    - class / interface (inheritance)
    - field / property
    - getter method / getter method parameter or getter return value
    - setter method / setter method parameter or setter parameter
    - container element
- :heavy_plus_sign: Custom annotations and validators
- :heavy_plus_sign: Support for all javax built-in constraints
- :heavy_plus_sign: Constraints groups
- :heavy_plus_sign: Support for validating container elements by annotating type arguments of parameterized types:
    - Cascaded validation of collection types e.g ``` List<@Positive Integer> positiveNumbers ```
    - Cascaded validation of map types e.g ``` Map<@Valid CustomerType, @Valid Customer> customersByType ```
    - Support for ``` java.util.Optional ```
- 

### List of :x: unsupported features 
- :x: XML validation descriptors
- :x: Message interpolation via the unified expression language
- :x: Annotation definitions target
    - constructors
    - methods other than setter / getter
    - cross-parameter constraints
- :x: Constraints payloads
- :x: @OverridesAttribute
- :x: Support for custom container types by plugging in additional value extractors 
- :x: Support for the property types declared by JavaFX 
- :x: Integration with Context and Dependency Injection 
- :x: Method validation 
- :x: Everything else connected with java bean validation 2.0 not mentioned in supported features section

## Usage
Every example can be found in `examples` module

Add `@Validator` to class which will be interface to be implemented by Beren. It must be either `abstract class or interface`. 
```java
@Validator
public abstract class FellowshipValidator {
}
```
`@Validator` has one parameter `BreakingStrategy` which allows to determine
how validator class will deal with invalid values.
- `THROW_ON_FIRST` Will force to throw `ValidationException` 
with proper violation message as soon as invalid value was found 
- `SUMMARIZE_ALL` will force to collect all violation messages.
 No exception will thrown, instead object of class `ValidationResults` will be returned.
 
By default `THROW_ON_FIRST` is used.
Let's add some validation method.
 
```java
@Validate(nullable = false, value = {
    @Field(name = "heroes", operation = "notEmpty")
})
abstract void checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing);
```
`@Validate` annotation values:
- `source` only available value for now is `METHOD_DEFINITION` which means validation is build based on Beren annotations. 
- `nullable` processor will create null check for input value. 
If nullable is set to false(default) violation will be created for 
message `nullableMessage`. If set to true validation will be skipped without violation.
- `nullableMessage` violation template. Default value is 
`%{input} must not be null!` where `%{input}` is name of method parameter
- `value` validation definitions for chosen fields.

Important notice!
- For `THROW_ON_FIRST` strategy method must return void
- For `SUMMARIZE_ALL` strategy method must return `ValidationResults`
- Only abstract method can be annotated with `@Validate`
- Validated method must have one and only one input parameter.

If you compile project now a java source file will be created 
in you target/generated-sources/annotations directory.
Beren will implement your class with name <YourClassName>ImplBeren_  
```java
public class FellowshipValidatorImplBeren_ extends FellowshipValidator {
    public FellowshipValidatorImplBeren_() {
        super();
    }

    @Override
    void checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing) {
        internal_checkFellowshipBeforeLeave(fellowshipOfTheRing);
    }

    private void internal_checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing) {
        if (fellowshipOfTheRing == null) {
            throw new ValidationException("fellowshipOfTheRing must not be null!");
        }
        if (!CollectionUtils.isNotEmpty(fellowshipOfTheRing.getHeroes())) {
            throw new ValidationException("Collection heroes must not be empty!");
        }
    }
}
```
As we can see messages are quite of of our project scope. 
Let's change them, but first a few words about `@Field` annotation used in example
`name = "heroes", operation = "notEmpty"`

For now there is only single field we want to validate and it's defined by its name.
Beren will try to find this field by getter in this case `getHeroes()`.
For this field we want to perform `notEmpty` operation. 
For your convenience every operation you use must check if field is valid, beren will negate value for you.
And therefore in generated code 
```java
if (!SequenceOperations.isNotEmpty(fellowshipOfTheRing.getHeroes()))
```   
You may now ask - Hey! Where did that `!SequenceOperations.isNotEmpty()` came from!?
There are three sources of validation method:
- Method annotated with `@Validate`
- Method already implemented in validator class
- Operation config defined in beren-operations-configuration.yaml

First two will be discussed later, for now let's focus on configuration file.
In `beren-compiler` module there is a file [beren-operations-configuration.yaml](beren-compiler/src/main/resources/beren-operations-configuration.yaml)
containing default operation mappings.
```yaml
between(min, max):
    operationCall: io.github.indesil.beren.operations.NumberOperations.between(this, min, max)
    defaultMessage: "{io.github.beren.validator.constraints.Between.message}"
notEmpty:
    operationCall: io.github.indesil.beren.operations.CollectionOperations.isNotEmpty(this)
    defaultMessage: "{javax.validation.constraints.NotEmpty.message}"
```       
Important notice:   
- Operation key is unique by its name, therefore arguments overloading is provided by operation call class.
- Operation arguments are be used as place holders in message as shown above.
- `this` argument indicate object that is being validated for example `fellowshipOfTheRing.getHeroes()`. 
Definition always contains `this` otherwise it's pointless.
- `defaultMessage` refers to property from which string template will be taken. 
Please check file [ValidationMessages](beren-compiler/src/main/resources/defaults/ValidationMessages.properties) to see defaults.

Let's replace nullable message and notEmpty message to fit our project.
```java
@Validate(nullable = false,
    nullableMessage = "Fellowship was not assembled! Is it all lost already?",
    value = {
    @Field(name = "heroes", operation = "notEmpty", message = "The ring must be destroyed! So... anyone volunteer?")
})
abstract void checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing);
``` 
`@Field(message)` always overrides default message configured for operation. 
Another way to change `notEmpty` default message is to create
`ValidationMessages.properties` in `resources` folder and then add entry
```properties
javax.validation.constraints.NotEmpty.message=My own default message for empty collection error - %{paramName} must not be empty!
```        
Compilation output may looks like this
```java
if(fellowshipOfTheRing == null) {
  throw new ValidationException("Fellowship was not assembled! Is it all lost already?");
}
// with configuration override
if(!SequenceOperations.isNotEmpty(fellowshipOfTheRing.getHeroes())) {
  throw new ValidationException("My own default message for empty collection error - `heroes` must not be empty!");
}
``` 
or with message override
```java
if(!SequenceOperations.isNotEmpty(fellowshipOfTheRing.getHeroes())) {
  throw new ValidationException("The ring must be destroyed! So... anyone volunteer?");
}
```
But what if you want to check each of heroes in the company?
There are two built in iteration command:
 - `#forEachKey(operation)` iterates over `Map.keySet()` and applies `operation` on each of value
 - `#forEachValue(operation)` iterates over `Map.values()` or `Collection` 
 and applies `operation` on each of value
 - Neither of iteration operation will check for null values! 
 It's is only guarantee that wrapper will replace null with empty collection `beren.CollectionUtils.collectionOrEmpty()`.
 Therefore if you want violation message for null collections you must add one by yourself!
 - `operation` may be `@Validate` method reference, our method that returns 
 boolean or any of configured operations that fit.
   
Let's add validation for each hero.
```java
@Validate(value = {
    @Field(names = {"name", "homeland", "weapon"}, operation = "notNull", message = "Have you forgotten something like %{paramName}?"),
    @Field(type = Integer.class, operation = "min(0)"),
    @Field(name = "age", operation = "min(18)", message = "You must be adult to go for adventure!"),
    @Field(name = "race", operation = "spiesCheck", message = "There is a Sauron spy in our team!"),
})
abstract void checkMember(Hero hero);
```
Important notice:
- `names` is list of fields names that behave same as `name`
 but without need of defining `@Field` for each of them.
- `type` Allows to apply `operation` for every field that is assignable to given Class.
It means that for Integer.class every field of type int.class 
and Integer.class will be validated with operation `min(0)`. Please make notice that 
if you use `Object.class` every field will apply since everything can be assigned to Object type!
- If multiple `@Field` definitions applies for one field then multiple validations 
will be generated for that field! Take a look at generated code for
`type=Integer.class` and `name=age` which is also Integer.
```java
if(!NumberOperations.min(hero.getAge(), 0)) {
  throw new ValidationException("age must be greater than or equal to 0");
}
if(!NumberOperations.min(hero.getHeight(), 0)) {
  throw new ValidationException("height must be greater than or equal to 0");
}
if(!NumberOperations.min(hero.getWeight(), 0)) {
  throw new ValidationException("weight must be greater or euqal to 0");
}
if(!NumberOperations.min(hero.getAge(), 18)) {
  throw new ValidationException("You must be adult to go for adventure!");
}
```
 In current version Beren cannot support Enums directly. You will need to add you validation method for them 
 ```java
@Id("spiesCheck")
boolean checkSpies(Race race) {
    return !GOBLIN.equals(race) && !ORC.equals(race);
}
```
`@Id("spiesCheck")` allows to create operation reference different than method name

Let's finish our validator by adding all of the rest validation definitions

```java
@Validate(nullable = false,
    nullableMessage = "Fellowship was not assembled! Is it all lost already?",
    value = {
    @Field(name = "heroes", operation = "notEmpty"),
    @Field(name = "heroes", operation = "#forEachValue(checkMember)"),
    @Field(name = "heroesStuff", operation = "#forEachKey(neitherOf(['Saruman', 'Balrog', 'Gollum']))", message = "Hey! Where did these things come from???"),
    @Field(name = "heroesStuff", operation = "#forEachValue(isThisGoodStuff)", message = "I think we need to check our supplies before leaving..."),
    @Field(name = "elrondAdvices", operation = "whatDidElrondSay")
})
abstract void checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing);

boolean isThisGoodStuff(Inventory inventory) {
    return inventory != null && inventory.getValue() > 0.0 && inventory.getName() != null;
}

@Validate(value = {
    @Field(names = {"name", "homeland", "weapon"}, operation = "notNull", message = "Have you forgotten something like %{paramName}?"),
    @Field(type = Integer.class, operation = "min(0)"),
    @Field(name = "age", operation = "min(18)", message = "You must be adult to go for adventure!"),
    @Field(name = "race", operation = "spiesCheck", message = "There is a Sauron spy in our team!"),
})
abstract void checkMember(Hero hero);

@Id("spiesCheck")
boolean checkSpies(Race race) {
    return !GOBLIN.equals(race) && !ORC.equals(race);
}

@Validate(value = {
    @Field(pattern = "advice.*", operation = "notNull", message = "Wait! I forgot %{paramName}")
})
abstract void whatDidElrondSay(VeryImportElrondAdvices advices);
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments
 
* Hat tip to [JavaPoet](https://github.com/square/javapoet)
* Inspired by [Selma](http://www.selma-java.org/)
* Special thanks to [JetBrains](https://www.jetbrains.com/?from=BEREN) for giving us open source Intellij license! 
![Jetbrains logo](logo/jetbrains/jetbrainslogo.png "Jetbrains logo")
