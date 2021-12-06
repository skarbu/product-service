FLAG=./rs_initiated.flag
if [ -f "$FLAG" ]; then
    echo "initialized already processed"
else
mongo <<EOF
print("----------------------- start custom script ---------------------")
var cfg = {
    "_id": "rs0",
    "version": 1,
    "members": [
        {
            "_id": 0,
            "host": "mongodb:27017",
            "priority": 2
        }
    ]
};
rs.initiate(cfg, { force: true })
print("----------------------- rs initiated ---------------------")
var status = rs.status.ok;
while(status == 1){
    status = rs.status.ok;
}

var admin = db.getSiblingDB("admin");
admin.createUser(
    {
        user: "root",
        pwd: "Lideo123^" ,
        roles: [ "root" ]
    }
)
EOF
echo 'rs_initiated' > $FLAG
fi

