i=1;
cnt=1;
while [[ $i -ne 0 ]]
do
./re.sh
./trans
res1=`cat transakce | grep "^1002 1000 5.00 ok$"`
if [[ -nz $res1 ]] 
then
i=0
fi

let "cnt++"
done
echo "pocet> $cnt"
cat transakce
