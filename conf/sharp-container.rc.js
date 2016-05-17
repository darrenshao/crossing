/*******************************************************************************
 * Default startup script
*******************************************************************************/
//------------------------------------------------------------------------------
//import class
//------------------------------------------------------------------------------
importPackage(com.ftxgame.sharp.container.res);
importPackage(com.ftxgame.sharp.container.res.ds);
importPackage(com.ftxgame.sharp.container.invoker);
importPackage(com.ftxgame.sharp.container.res.config);
importPackage(com.ftxgame.sharp.rpcserver);
//------------------------------------------------------------------------------
//global vars
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
//setup log level
//------------------------------------------------------------------------------
$.setLogLevel("INFO");
//$.enableColorOutput();
$.setLogFile("./log/example.log");
//
//app servers
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------


//------------------------------------------------------------------------------
//serverList最后两行的配置是游戏服务器的配置，增加游戏的时候按照最后两行的格式增加

//rpcServer=new RPCServer();
//rpcServer.port=9901;
//$.addServer(rpcServer);



var serverList=[
{cluster:"SharpDemoServer",name:"app1",host:"127.0.0.1",port:9901},
{cluster:"SharpHelloServer",name:"app1",host:"127.0.0.1",port:9901},
];
for(i=0;i<serverList.length;i++){
       server=serverList[i];
       remoteServer=new ServerInfo();
       remoteServer.setName(server.name);
       remoteServer.setCluster(server.cluster);
       remoteServer.setHost(server.host);
       remoteServer.setPort(server.port);
       $.addRemoteServer(remoteServer);
}


