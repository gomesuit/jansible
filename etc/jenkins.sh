rm -rf $projectName
git clone $repositoryUrl $projectName
cd $projectName
git checkout $tagName
git submodule init
git submodule update
ansible-playbook -i $hostsFileName $groupName.yml --syntax-check
ansible-playbook -i $hostsFileName $groupName.yml --list-tasks
ansible-playbook -i $hostsFileName $groupName.yml --list-hosts
ansible-playbook -i $hostsFileName $groupName.yml
echo $projectName
echo $repositoryUrl
echo $groupName
echo $tagName
echo $applyHistroyId
echo $hostsFileName
