/* Generated By:JavaCC: Do not edit this line. XMLTokenManager.java */
package contacts.parser;

/** Token Manager. */
public class XMLTokenManager implements XMLConstants
{
  String attr;

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x1eL) != 0L)
            return 3;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 9:
         return jjStartNfaWithStates_0(0, 2, 3);
      case 10:
         return jjStartNfaWithStates_0(0, 4, 3);
      case 13:
         return jjStartNfaWithStates_0(0, 3, 3);
      case 32:
         return jjStartNfaWithStates_0(0, 1, 3);
      case 60:
         return jjMoveStringLiteralDfa1_0(0x1fffe0L);
      default :
         return jjMoveNfa_0(3, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 47:
         return jjMoveStringLiteralDfa2_0(active0, 0xaaac0L);
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x20L);
      case 99:
         return jjMoveStringLiteralDfa2_0(active0, 0x100L);
      case 102:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000L);
      case 103:
         return jjMoveStringLiteralDfa2_0(active0, 0x100000L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000L);
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x1400L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x440L);
      case 99:
         return jjMoveStringLiteralDfa3_0(active0, 0x200L);
      case 100:
         return jjMoveStringLiteralDfa3_0(active0, 0x40020L);
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000L);
      case 103:
         return jjMoveStringLiteralDfa3_0(active0, 0x80L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x80000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x2800L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x8100L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x110000L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000L);
      case 119:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 62:
         if ((active0 & 0x40000L) != 0L)
            return jjStopAtPos(3, 18);
         break;
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x800L);
      case 100:
         return jjMoveStringLiteralDfa4_0(active0, 0x80060L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000L);
      case 109:
         return jjMoveStringLiteralDfa4_0(active0, 0x1400L);
      case 110:
         return jjMoveStringLiteralDfa4_0(active0, 0x4100L);
      case 111:
         return jjMoveStringLiteralDfa4_0(active0, 0x100200L);
      case 114:
         return jjMoveStringLiteralDfa4_0(active0, 0x20080L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000L);
      case 119:
         return jjMoveStringLiteralDfa4_0(active0, 0x8000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 62:
         if ((active0 & 0x80000L) != 0L)
            return jjStopAtPos(4, 19);
         break;
      case 98:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000L);
      case 100:
         return jjMoveStringLiteralDfa5_0(active0, 0x40L);
      case 101:
         return jjMoveStringLiteralDfa5_0(active0, 0x10400L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x24000L);
      case 109:
         return jjMoveStringLiteralDfa5_0(active0, 0x2800L);
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x8200L);
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x80L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x20L);
      case 116:
         return jjMoveStringLiteralDfa5_0(active0, 0x100L);
      case 117:
         return jjMoveStringLiteralDfa5_0(active0, 0x100000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 62:
         if ((active0 & 0x400L) != 0L)
            return jjStopAtPos(5, 10);
         break;
      case 97:
         return jjMoveStringLiteralDfa6_0(active0, 0x100L);
      case 98:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000L);
      case 100:
         return jjMoveStringLiteralDfa6_0(active0, 0x4000L);
      case 101:
         return jjMoveStringLiteralDfa6_0(active0, 0x21820L);
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x8000L);
      case 110:
         return jjMoveStringLiteralDfa6_0(active0, 0x10000L);
      case 112:
         return jjMoveStringLiteralDfa6_0(active0, 0x100000L);
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x40L);
      case 116:
         return jjMoveStringLiteralDfa6_0(active0, 0x200L);
      case 117:
         return jjMoveStringLiteralDfa6_0(active0, 0x80L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 32:
         return jjMoveStringLiteralDfa7_0(active0, 0x100000L);
      case 62:
         if ((active0 & 0x800L) != 0L)
            return jjStopAtPos(6, 11);
         else if ((active0 & 0x4000L) != 0L)
            return jjStopAtPos(6, 14);
         break;
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x200L);
      case 99:
         return jjMoveStringLiteralDfa7_0(active0, 0x100L);
      case 100:
         return jjMoveStringLiteralDfa7_0(active0, 0x18000L);
      case 101:
         return jjMoveStringLiteralDfa7_0(active0, 0x2040L);
      case 110:
         return jjMoveStringLiteralDfa7_0(active0, 0x20000L);
      case 112:
         return jjMoveStringLiteralDfa7_0(active0, 0x80L);
      case 114:
         return jjMoveStringLiteralDfa7_0(active0, 0x1000L);
      case 115:
         return jjMoveStringLiteralDfa7_0(active0, 0x20L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 62:
         if ((active0 & 0x80L) != 0L)
            return jjStopAtPos(7, 7);
         else if ((active0 & 0x1000L) != 0L)
            return jjStopAtPos(7, 12);
         else if ((active0 & 0x8000L) != 0L)
            return jjStopAtPos(7, 15);
         break;
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x200L);
      case 100:
         return jjMoveStringLiteralDfa8_0(active0, 0x20000L);
      case 110:
         return jjMoveStringLiteralDfa8_0(active0, 0x100000L);
      case 114:
         return jjMoveStringLiteralDfa8_0(active0, 0x2000L);
      case 115:
         return jjMoveStringLiteralDfa8_0(active0, 0x10060L);
      case 116:
         return jjMoveStringLiteralDfa8_0(active0, 0x100L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
private int jjMoveStringLiteralDfa8_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 62:
         if ((active0 & 0x100L) != 0L)
            return jjStopAtPos(8, 8);
         else if ((active0 & 0x2000L) != 0L)
            return jjStopAtPos(8, 13);
         else if ((active0 & 0x10000L) != 0L)
            return jjStopAtPos(8, 16);
         break;
      case 66:
         return jjMoveStringLiteralDfa9_0(active0, 0x20L);
      case 97:
         return jjMoveStringLiteralDfa9_0(active0, 0x100000L);
      case 115:
         return jjMoveStringLiteralDfa9_0(active0, 0x20040L);
      case 116:
         return jjMoveStringLiteralDfa9_0(active0, 0x200L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
private int jjMoveStringLiteralDfa9_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 62:
         if ((active0 & 0x200L) != 0L)
            return jjStopAtPos(9, 9);
         else if ((active0 & 0x20000L) != 0L)
            return jjStopAtPos(9, 17);
         break;
      case 66:
         return jjMoveStringLiteralDfa10_0(active0, 0x40L);
      case 109:
         return jjMoveStringLiteralDfa10_0(active0, 0x100000L);
      case 111:
         return jjMoveStringLiteralDfa10_0(active0, 0x20L);
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
private int jjMoveStringLiteralDfa10_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa11_0(active0, 0x100000L);
      case 111:
         return jjMoveStringLiteralDfa11_0(active0, 0x60L);
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
private int jjMoveStringLiteralDfa11_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(9, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(10, active0);
      return 11;
   }
   switch(curChar)
   {
      case 61:
         return jjMoveStringLiteralDfa12_0(active0, 0x100000L);
      case 107:
         return jjMoveStringLiteralDfa12_0(active0, 0x20L);
      case 111:
         return jjMoveStringLiteralDfa12_0(active0, 0x40L);
      default :
         break;
   }
   return jjStartNfa_0(10, active0);
}
private int jjMoveStringLiteralDfa12_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(10, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(11, active0);
      return 12;
   }
   switch(curChar)
   {
      case 34:
         if ((active0 & 0x100000L) != 0L)
            return jjStopAtPos(12, 20);
         break;
      case 62:
         if ((active0 & 0x20L) != 0L)
            return jjStopAtPos(12, 5);
         break;
      case 107:
         return jjMoveStringLiteralDfa13_0(active0, 0x40L);
      default :
         break;
   }
   return jjStartNfa_0(11, active0);
}
private int jjMoveStringLiteralDfa13_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(11, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(12, active0);
      return 13;
   }
   switch(curChar)
   {
      case 62:
         if ((active0 & 0x40L) != 0L)
            return jjStopAtPos(13, 6);
         break;
      default :
         break;
   }
   return jjStartNfa_0(12, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 3;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 3:
                  if ((0xafffffffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(0, 1);
                  if ((0xaffffffeffffd9ffL & l) != 0L)
                  {
                     if (kind > 24)
                        kind = 24;
                     jjCheckNAddTwoStates(1, 2);
                  }
                  break;
               case 0:
                  if ((0xafffffffffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(0, 1);
                  break;
               case 1:
                  if ((0xaffffffeffffd9ffL & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(1, 2);
                  break;
               case 2:
                  if ((0xafffffffffffffffL & l) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAdd(2);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 3:
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(1, 2);
                  jjCheckNAddTwoStates(0, 1);
                  break;
               case 0:
                  jjCheckNAddTwoStates(0, 1);
                  break;
               case 1:
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(1, 2);
                  break;
               case 2:
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAdd(2);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 3:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(0, 1);
                  if ((jjbitVec0[i2] & l2) != 0L)
                  {
                     if (kind > 24)
                        kind = 24;
                     jjCheckNAddTwoStates(1, 2);
                  }
                  break;
               case 0:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjCheckNAddTwoStates(0, 1);
                  break;
               case 1:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAddTwoStates(1, 2);
                  break;
               case 2:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 24)
                     kind = 24;
                  jjCheckNAdd(2);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private int jjMoveStringLiteralDfa0_2()
{
   return jjMoveNfa_2(0, 0);
}
private int jjMoveNfa_2(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 1;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0xfffffffbffffffffL & l) == 0L)
                     break;
                  kind = 21;
                  jjstateSet[jjnewStateCnt++] = 0;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  kind = 21;
                  jjstateSet[jjnewStateCnt++] = 0;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjstateSet[jjnewStateCnt++] = 0;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 1 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 62:
         return jjStopAtPos(0, 23);
      default :
         return 1;
   }
}
private int jjMoveStringLiteralDfa0_3()
{
   switch(curChar)
   {
      case 34:
         return jjStopAtPos(0, 22);
      default :
         return 1;
   }
}
static final int[] jjnextStates = {
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, 
"\74\141\144\144\162\145\163\163\102\157\157\153\76", "\74\57\141\144\144\162\145\163\163\102\157\157\153\76", 
"\74\57\147\162\157\165\160\76", "\74\143\157\156\164\141\143\164\76", "\74\57\143\157\156\164\141\143\164\76", 
"\74\156\141\155\145\76", "\74\57\156\141\155\145\76", "\74\156\165\155\142\145\162\76", 
"\74\57\156\165\155\142\145\162\76", "\74\157\167\156\151\144\76", "\74\57\157\167\156\151\144\76", 
"\74\146\162\151\145\156\144\163\76", "\74\57\146\162\151\145\156\144\163\76", "\74\151\144\76", 
"\74\57\151\144\76", null, null, null, null, null, };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
   "IN_OPENGROUP3",
   "IN_OPENGROUP",
   "IN_OPENGROUP2",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, 3, 1, 0, -1, 
};
static final long[] jjtoToken = {
   0x18fffe1L, 
};
static final long[] jjtoSkip = {
   0x1eL, 
};
static final long[] jjtoMore = {
   0x700000L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[3];
private final int[] jjstateSet = new int[6];
private final StringBuilder jjimage = new StringBuilder();
private StringBuilder image = jjimage;
private int jjimageLen;
private int lengthOfMatch;
protected char curChar;
/** Constructor. */
public XMLTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public XMLTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 3; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 4 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   if (jjmatchedPos < 0)
   {
      if (image == null)
         curTokenImage = "";
      else
         curTokenImage = image.toString();
      beginLine = endLine = input_stream.getBeginLine();
      beginColumn = endColumn = input_stream.getBeginColumn();
   }
   else
   {
      String im = jjstrLiteralImages[jjmatchedKind];
      curTokenImage = (im == null) ? input_stream.GetImage() : im;
      beginLine = input_stream.getBeginLine();
      beginColumn = input_stream.getBeginColumn();
      endLine = input_stream.getEndLine();
      endColumn = input_stream.getEndColumn();
   }
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }
   image = jjimage;
   image.setLength(0);
   jjimageLen = 0;

   for (;;)
   {
     switch(curLexState)
     {
       case 0:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_0();
         break;
       case 1:
         try { input_stream.backup(0);
            while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
               curChar = input_stream.BeginToken();
         }
         catch (java.io.IOException e1) { continue EOFLoop; }
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_1();
         break;
       case 2:
         jjmatchedKind = 21;
         jjmatchedPos = -1;
         curPos = 0;
         curPos = jjMoveStringLiteralDfa0_2();
         break;
       case 3:
         jjmatchedKind = 0x7fffffff;
         jjmatchedPos = 0;
         curPos = jjMoveStringLiteralDfa0_3();
         break;
     }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
           TokenLexicalActions(matchedToken);
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
        MoreLexicalActions();
      if (jjnewLexState[jjmatchedKind] != -1)
        curLexState = jjnewLexState[jjmatchedKind];
        curPos = 0;
        jjmatchedKind = 0x7fffffff;
        try {
           curChar = input_stream.readChar();
           continue;
        }
        catch (java.io.IOException e1) { }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
   }
  }
}

void MoreLexicalActions()
{
   jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
   switch(jjmatchedKind)
   {
      case 20 :
         image.append(input_stream.GetSuffix(jjimageLen));
         jjimageLen = 0;
                      image.setLength(0);
         break;
      case 21 :
         image.append(input_stream.GetSuffix(jjimageLen));
         jjimageLen = 0;
                 attr = ""+image;
         break;
      default :
         break;
   }
}
void TokenLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      case 23 :
        image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
                     matchedToken.attribute = attr.trim();
         break;
      case 24 :
        image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
         matchedToken.attribute = matchedToken.image.trim();
         break;
      default :
         break;
   }
}
private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

}