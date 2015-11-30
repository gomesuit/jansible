rm -rf $projectName
git clone $repositoryUrl $projectName
cd $projectName
git checkout $tagName
git submodule init
git submodule update
ansible-playbook -i hosts $groupName.yml --syntax-check
ansible-playbook -i hosts $groupName.yml --list-tasks
ansible-playbook -i hosts $groupName.yml --list-hosts
ansible-playbook -i hosts $groupName.yml --check
echo $projectName
echo $repositoryUrl
echo $groupName
echo $tagName
echo $applyHistroyId
