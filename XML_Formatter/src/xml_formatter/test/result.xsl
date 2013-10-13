<?xml version="1.0" encoding="UTF-8"?>


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"    version="2.0"      >



  <xsl:include href="include.xsl" />
  

  <xsl:variable 
      name="abc" 

      select="777" />
      


  <xsl:template match="/"> 
  


    das ist ein <xsl:value-of select="'sehr langer'" /> Text, sodass er wahrscheinlich umgebrochen werden muss
    


    <xsl:call-template name="temp" /> 
    


  </xsl:template>
  


  <xsl:template name="temp">
  
  




    <xsl:variable name="zero"      >00</xsl:variable>

    <xsl:variable 



        name="asd">213</xsl:variable>
        

    <xsl:value-of 

        select="$abc" />

    <xsl:value-of 

        select="$zero  " /> 
        


    <element value="abs">




      <xsl:variable name="asd">55</xsl:variable>


      <xsl:value-of select="$asd" />


    </element>
    


    <element>


      <xsl:value-of select="$asd" />


    </element>
    


    <xsl:value-of select="func()" />


  </xsl:template>
  
  


  <xsl:function name="func">


    <test>


      <xsl:value-of select="$xyz" />


    </test>
    


  </xsl:function>
  


</xsl:stylesheet>