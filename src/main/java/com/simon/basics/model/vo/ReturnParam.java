package com.simon.basics.model.vo;

/**
 * @author fengtianying
 * @date 2018/9/4 13:25
 */
public class ReturnParam<T> extends CodeParam {

    private String code;

    private String msg;

    private T data;

    public ReturnParam() {
        super();
    }

    public ReturnParam(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ReturnParam success(String msg,Object data){
        return new ReturnParam(success,msg,data);
    }

    public static ReturnParam success(){
        return new ReturnParam(success,"成功",null);
    }

    public static ReturnParam success(String msg){
        return new ReturnParam(success,msg,null);
    }

    public static ReturnParam courseEnding(){
        return new ReturnParam(courseEnding,courseEndingMsg,null);
    }
    public static ReturnParam lastCourseNoEnding(){
        return new ReturnParam(lastCourseNoEnding,lastCourseNoEndingMsg,null);
    }
    public static ReturnParam repeatOrder(){
        return new ReturnParam(repeatOrder,repeatOrderMsg,null);
    }
    public static ReturnParam courseNotBeginning(){
        return new ReturnParam(courseNotBeginning,courseNotBeginningMsg,null);
    }
    public static ReturnParam repeatResource(String msg){
        return new ReturnParam(repeatResource,repeatResourceMsg+":"+msg,null);
    }
    public static ReturnParam alreadyChoose(){
        return new ReturnParam(alreadyChoose,alreadyChooseMsg,null);
    }

    public static ReturnParam courseActing(){
        return new ReturnParam(courseActing,courseActingMsg,null);
    }

    public static ReturnParam courseActite(){
        return new ReturnParam(courseActite,courseActiteMsg,null);
    }

    public static ReturnParam courseNotEnoughOrNotHad(){
        return new ReturnParam(courseNotEnoughOrNotHad,courseNotEnoughOrNotHadMsg,null);
    }

    public static ReturnParam success(Object data){
        return new ReturnParam(success,"成功",data);
    }

    public static ReturnParam fail(String code,String msg,Object data){
        return new ReturnParam(missParam,msg,data);
    }

    public static ReturnParam systemError(String msg){
        return new ReturnParam(systemError,msg,null);
    }

    public static ReturnParam missParam(String msg){
        return new ReturnParam(missParam,msg,null);
    }
    public static ReturnParam paramiolationException(String msg){
        return new ReturnParam(paramiolationException,msg,null);
    }
    public static ReturnParam illegalKeyIdException(){
        return new ReturnParam(illegalKeyIdException,illegalKeyIdExceptionMsg,null);
    }
    public static ReturnParam sqlWritePrerequisiteException(){
        return new ReturnParam(sqlWritePrerequisiteException,sqlWritePrerequisiteExceptionMsg,null);
    }

    public static ReturnParam noHandlerFound(String msg){
        return new ReturnParam(noHandlerFound,msg,null);
    }

    public static ReturnParam sessionOverdue(){
        return new ReturnParam(sessionOverdue,sessionOverdueMsg,null);
    }

    public static ReturnParam incorrectCredentials(){
        return new ReturnParam(incorrectCredentials,incorrectCredentialsMsg,null);
    }

    public static ReturnParam userExist(){
        return new ReturnParam(userExist,userExistMsg,null);
    }

    public static ReturnParam verifing(){
        return new ReturnParam(verifing,verifingMsg,null);
    }

    public static ReturnParam noVerification(){
        return new ReturnParam(noVerification,noVerificationMsg,null);
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReturnParam{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
