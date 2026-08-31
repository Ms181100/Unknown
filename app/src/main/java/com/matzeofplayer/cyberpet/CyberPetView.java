package com.matzeofplayer.cyberpet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

public class CyberPetView extends View {
    private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private long coins = 999_999_999L;
    private int level = 100;
    private int food = 100, clean = 100, sleep = 100, mood = 100;
    private int room = 2;
    private boolean maxMode = true;
    private String message = "RED CORE ONLINE";

    public CyberPetView(Context context) {
        super(context);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(4f);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override protected void onDraw(Canvas c) {
        super.onDraw(c);
        int w=getWidth(), h=getHeight();
        p.setShader(new LinearGradient(0,0,0,h,Color.rgb(5,7,10),Color.rgb(25,3,8), Shader.TileMode.CLAMP));
        c.drawRect(0,0,w,h,p); p.setShader(null);

        p.setColor(Color.rgb(255,35,60));
        for(int i=0;i<8;i++) c.drawRect(0,h*(.12f+i*.08f),w,h*(.121f+i*.08f),p);

        panel(c,w*.03f,h*.03f,w*.42f,h*.105f,Color.rgb(12,14,18));
        p.setColor(Color.WHITE); p.setTextSize(w*.038f); p.setFakeBoldText(true);
        c.drawText("◐  "+String.format("%,d",coins),w*.055f,h*.078f,p);

        panel(c,w*.58f,h*.03f,w*.97f,h*.105f,Color.rgb(12,14,18));
        p.setColor(Color.WHITE); p.setTextSize(w*.028f); c.drawText("LEVEL "+level+"   MAX",w*.62f,h*.078f,p);

        String[] roomNames={"KÜCHE","BAD","HAUPTRAUM","SCHLAFEN","DRAUSSEN"};
        p.setColor(Color.argb(80,255,35,60)); p.setTextAlign(Paint.Align.CENTER); p.setTextSize(w*.08f);
        c.drawText(roomNames[room],w*.5f,h*.23f,p); p.setTextAlign(Paint.Align.LEFT);

        drawPet(c,w,h);
        drawNeeds(c,w,h);
        drawButtons(c,w,h);
        drawRooms(c,w,h);

        p.setColor(Color.rgb(255,55,75)); p.setTextAlign(Paint.Align.CENTER); p.setTextSize(w*.022f);
        c.drawText(message,w*.5f,h*.67f,p); p.setTextAlign(Paint.Align.LEFT);
    }

    private void drawPet(Canvas c,int w,int h){
        float cx=w*.5f, cy=h*.43f, bw=w*.40f, bh=h*.28f;
        RectF body=new RectF(cx-bw/2,cy-bh/2,cx+bw/2,cy+bh/2);
        p.setShader(new LinearGradient(body.left,body.top,body.right,body.bottom,Color.rgb(225,225,230),Color.rgb(95,100,108),Shader.TileMode.CLAMP));
        c.drawRoundRect(body,bw*.30f,bw*.30f,p); p.setShader(null);
        stroke.setColor(Color.rgb(25,28,32)); stroke.setStrokeWidth(w*.008f); c.drawRoundRect(body,bw*.30f,bw*.30f,stroke);

        Path mech=new Path();
        mech.moveTo(cx,body.top); mech.lineTo(body.right,body.top+bh*.17f); mech.lineTo(body.right,body.bottom-bh*.12f); mech.lineTo(cx,body.bottom); mech.close();
        p.setColor(Color.rgb(30,32,36)); c.drawPath(mech,p);
        p.setColor(Color.rgb(85,90,98));
        for(int i=0;i<7;i++) c.drawRect(cx+bw*.06f,body.top+bh*(.15f+i*.10f),body.right-bw*.04f,body.top+bh*(.18f+i*.10f),p);

        p.setColor(Color.rgb(18,20,22)); c.drawCircle(cx-bw*.18f,cy-bh*.10f,bw*.09f,p);
        p.setColor(Color.WHITE); c.drawCircle(cx-bw*.20f,cy-bh*.12f,bw*.028f,p);

        float pulse=0.8f+0.2f*(float)Math.sin(System.currentTimeMillis()/120.0);
        p.setShadowLayer(w*.04f*pulse,0,0,Color.RED); p.setColor(Color.rgb(255,25,45));
        c.drawCircle(cx+bw*.18f,cy-bh*.08f,bw*.10f,p); p.clearShadowLayer();
        p.setColor(Color.rgb(255,170,100)); c.drawCircle(cx+bw*.18f,cy-bh*.08f,bw*.032f,p);

        Path smile=new Path(); smile.moveTo(cx-bw*.09f,cy+bh*.08f); smile.quadTo(cx,cy+bh*.16f,cx+bw*.09f,cy+bh*.08f);
        stroke.setColor(Color.rgb(20,20,22)); stroke.setStrokeWidth(w*.007f); c.drawPath(smile,stroke);

        p.setColor(Color.rgb(125,130,138));
        c.drawOval(new RectF(cx-bw*.34f,body.bottom-bh*.02f,cx-bw*.04f,body.bottom+bh*.14f),p);
        c.drawOval(new RectF(cx+bw*.04f,body.bottom-bh*.02f,cx+bw*.34f,body.bottom+bh*.14f),p);
    }

    private void drawNeeds(Canvas c,int w,int h){
        String[] n={"ESSEN","SAUBER","SCHLAF","LAUNE"}; int[] v={food,clean,sleep,mood};
        for(int i=0;i<4;i++){
            float l=w*(.03f+i*.245f), r=l+w*.22f, t=h*.71f, b=h*.79f;
            panel(c,l,t,r,b,Color.rgb(15,18,22));
            p.setColor(Color.WHITE); p.setTextSize(w*.016f); c.drawText(n[i],l+w*.015f,t+h*.024f,p);
            p.setColor(Color.rgb(55,60,67)); c.drawRoundRect(new RectF(l+w*.015f,t+h*.038f,r-w*.015f,t+h*.052f),8,8,p);
            p.setColor(i==0?Color.rgb(100,255,40):i==1?Color.rgb(40,190,255):i==2?Color.rgb(200,90,255):Color.rgb(255,190,30));
            c.drawRoundRect(new RectF(l+w*.015f,t+h*.038f,l+w*.015f+(r-l-w*.03f)*(v[i]/100f),t+h*.052f),8,8,p);
            p.setColor(Color.WHITE); p.setTextSize(w*.015f); c.drawText(v[i]+"%",l+w*.08f,t+h*.070f,p);
        }
    }

    private void drawButtons(Canvas c,int w,int h){
        String[] labels={"FÜTTERN","PUTZEN","SCHLAF","SPIELEN","MAX"};
        for(int i=0;i<5;i++){
            float l=w*(.03f+i*.194f), r=l+w*.175f, t=h*.815f, b=h*.875f;
            panel(c,l,t,r,b,i==4&&maxMode?Color.rgb(35,90,25):Color.rgb(18,20,24));
            p.setColor(Color.WHITE); p.setTextAlign(Paint.Align.CENTER); p.setTextSize(w*.016f);
            c.drawText(labels[i],(l+r)/2,t+h*.038f,p); p.setTextAlign(Paint.Align.LEFT);
        }
    }

    private void drawRooms(Canvas c,int w,int h){
        String[] labels={"Küche","Bad","Main","Schlaf","Draußen"};
        for(int i=0;i<5;i++){
            float l=w*(.03f+i*.194f), r=l+w*.175f, t=h*.895f, b=h*.965f;
            panel(c,l,t,r,b,i==room?Color.rgb(70,18,25):Color.rgb(14,16,20));
            p.setColor(i==room?Color.rgb(255,60,80):Color.WHITE); p.setTextAlign(Paint.Align.CENTER); p.setTextSize(w*.016f);
            c.drawText(labels[i],(l+r)/2,t+h*.043f,p); p.setTextAlign(Paint.Align.LEFT);
        }
    }

    private void panel(Canvas c,float l,float t,float r,float b,int fill){
        p.setColor(fill); c.drawRoundRect(new RectF(l,t,r,b),18,18,p);
        stroke.setColor(Color.rgb(255,35,60)); stroke.setStrokeWidth(Math.max(2,getWidth()*.0025f)); c.drawRoundRect(new RectF(l,t,r,b),18,18,stroke);
    }

    @Override public boolean onTouchEvent(MotionEvent e){
        if(e.getAction()!=MotionEvent.ACTION_UP) return true;
        float x=e.getX(), y=e.getY(); int w=getWidth(), h=getHeight();
        if(y>=h*.815f && y<=h*.875f){
            int i=Math.min(4,Math.max(0,(int)((x-w*.03f)/(w*.194f))));
            if(i==0){food=100; message="ENERGIE AUFGENOMMEN";}
            else if(i==1){clean=100; message="SYSTEM GEREINIGT";}
            else if(i==2){sleep=100; message="AKKU REGENERIERT";}
            else if(i==3){mood=100; message="LAUNE MAX";}
            else {maxMode=!maxMode; message=maxMode?"MAX-MODUS AKTIV":"MAX-MODUS AUS";}
        } else if(y>=h*.895f && y<=h*.965f){
            room=Math.min(4,Math.max(0,(int)((x-w*.03f)/(w*.194f)))); message="RAUM GELADEN";
        }
        if(maxMode) food=clean=sleep=mood=100;
        invalidate(); return true;
    }
}
