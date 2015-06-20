package org.loader.pojo.ft;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FtGlEntity {

    public FtGlEntity() {
    }

    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (!(object instanceof FtGlEntity))
            return false;

        FtGlEntity other = (FtGlEntity) object;

        if (this.glSeqNbr != other.glSeqNbr) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return glSeqNbr;
    }

    @Column(name = "AMOUNT")
    public double amount = 0;

    @Column(name = "CHAR_TYPE_CD", columnDefinition = "char", length = 8)
    public String charTypeCd = " ";

    @Column(name = "CHAR_VAL", columnDefinition = "char", length = 16)
    public String charVal = " ";

    @Column(name = "DST_ID", columnDefinition = "char", length = 10)
    public String dstId = " ";

    @Column(name = "GL_ACCT", length = 254)
    public String glAcct = " ";

    @Column(name = "GL_SEQ_NBR")
    public int glSeqNbr;

    @Column(name = "STATISTIC_AMOUNT")
    public double statisticAmount = 0;

    @Column(name = "TOT_AMT_SW", columnDefinition = "char", length = 1)
    public String totAmtSw = " ";

    @Column(name = "VERSION")
    public int version = 1;


}